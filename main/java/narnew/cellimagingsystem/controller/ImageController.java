package narnew.cellimagingsystem.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import narnew.cellimagingsystem.base.Response;
import narnew.cellimagingsystem.entity.UserInfoDto;
import narnew.cellimagingsystem.service.HistoryService;
import narnew.cellimagingsystem.service.ImageService;
import narnew.cellimagingsystem.service.UserService;
import narnew.cellimagingsystem.vo.ImageHistoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    private ImageService imageService;
    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserService userService;


    @PostMapping("/upload")
    @ApiOperation("图片上传")
    public Response<String> uploadFile(MultipartFile multipartFile, HttpServletRequest request, int type) {
        //获取当前用户
        UserInfoDto loginUser = userService.getLoginUser(request);
        String userId = loginUser.getId();
        //处理图片 获取处理前后图片id
        String[] imgIdArr = imageService.deal(multipartFile, userId,type);
        //添加历史记录
        historyService.addRecord(imgIdArr,userId,type);
        //返回处理后图片的id
        return Response.with(imgIdArr[1]);
    }

    /**
     * 查询图片历史记录
     * 普通用户只能看到自己的记录；管理员可以看到所有
     * @return list
     */
    @GetMapping("/historyList")
    @ApiOperation("历史列表")
    public Response<List<ImageHistoryVo>> imageList(HttpServletRequest request,Integer type){

        UserInfoDto loginUser = userService.getLoginUser(request);
        List<ImageHistoryVo> res = historyService.listHistory(type, loginUser);
        return Response.with(res);
    }
    @PutMapping("{historyId}")
    @ApiOperation("修改历史备注")
    public Response<String> updateNote(@PathVariable String historyId,String note){
        historyService.updateNote(historyId,note);
        return Response.with();
    }


    /**
     * 通过文件id预览
     *
     * @param id       文件id
     * @param response res
     */
    @GetMapping("/preview/{id}")
    @ApiOperation("文件预览-path")
    public void previewFileByPath(@PathVariable String id, HttpServletResponse response) {
        imageService.preview(id, response);
    }

}
