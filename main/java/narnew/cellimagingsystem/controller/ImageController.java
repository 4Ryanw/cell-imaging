package narnew.cellimagingsystem.controller;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import narnew.cellimagingsystem.base.Response;
import narnew.cellimagingsystem.entity.CellImage;
import narnew.cellimagingsystem.entity.ImageProcessingHistory;
import narnew.cellimagingsystem.service.HistoryService;
import narnew.cellimagingsystem.service.ImageService;
import narnew.cellimagingsystem.utils.ImageUtil;
import narnew.cellimagingsystem.vo.ImageHistoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * TODO
 *
 * @author narnew
 * @since 2024年05月12日
 */
@RestController
@RequestMapping("/image")
@Api(tags = "图片相关")
public class ImageController {
    @Autowired
    ImageService imageService;
    @Autowired
    HistoryService historyService;

    @PostMapping("/upload")
    @ApiOperation("图片上传")
    public Response<List<CellImage>> uploadFile(MultipartFile multipartFile, String userId, int type) throws IOException {
        ArrayList<CellImage> resList = new ArrayList<>();
        CellImage cellImage = new CellImage();
        File file = new File(ImageUtil.saveTemp(multipartFile, ImageUtil.getFileType(multipartFile)));

        //将图片转为base64
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }

        byte[] imageBytes = bos.toByteArray();

        fis.close();
        bos.close();
        cellImage.setUserId(userId);
        cellImage.setContent(Base64.getEncoder().encodeToString(imageBytes));
        imageService.save(cellImage);
        //添加历史记录
        ImageProcessingHistory imageProcessingHistory = new ImageProcessingHistory();

        imageProcessingHistory.setImageIdBefore(cellImage.getId());
        //todo 待修改  先固定一张
        CellImage fix1;
        if (type==1){
            fix1  = imageService.getById("1789860595933700098");
        }else {
            fix1  = imageService.getById("1789860611318407169");
        }

        imageProcessingHistory.setImageIdAfter(fix1.getId());
        imageProcessingHistory.setUserId(userId);
        historyService.save(imageProcessingHistory);

        resList.add(cellImage);
        resList.add(fix1);
        return Response.with(resList);
    }

    /**
     * 根据用户id查询图片历史记录
     * @return list
     */
    @GetMapping("/historyList/{userId}")
    @ApiOperation("历史列表")
    public Response<List<ImageHistoryVo>> imageList(@PathVariable String userId){
        LinkedList<ImageHistoryVo> res = new LinkedList<>();
        LambdaQueryWrapper<ImageProcessingHistory> lqw = new LambdaQueryWrapper();
        lqw.eq(ImageProcessingHistory::getUserId,userId).orderByDesc(ImageProcessingHistory::getCreatedTime);
        List<ImageProcessingHistory> list = historyService.list(lqw);
        for (ImageProcessingHistory imageProcessingHistory : list) {
            ImageHistoryVo imageHistoryVo = new ImageHistoryVo();
            CellImage before = imageService.getById(imageProcessingHistory.getImageIdBefore());
            CellImage after = imageService.getById(imageProcessingHistory.getImageIdAfter());
            imageHistoryVo.setDate(before.getCreatedTime());
            imageHistoryVo.setBase64_before(before.getContent());
            imageHistoryVo.setBase64_after(after.getContent());
            res.add(imageHistoryVo);
        }
        return Response.with(res);
    }
}
