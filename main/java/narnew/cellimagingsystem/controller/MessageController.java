package narnew.cellimagingsystem.controller;

import cn.hutool.core.lang.tree.Tree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import narnew.cellimagingsystem.CoreException;
import narnew.cellimagingsystem.base.Response;
import narnew.cellimagingsystem.entity.Message;
import narnew.cellimagingsystem.entity.UserInfoDto;
import narnew.cellimagingsystem.enums.ErrorCodeEnum;
import narnew.cellimagingsystem.service.MessageService;
import narnew.cellimagingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * TODO
 *
 * @author narnew
 * @since 2024年05月12日
 */
@RestController
@RequestMapping("/image")
@Api(tags = "消息留言")
public class MessageController {
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;
    //新增消息
    @PostMapping
    @ApiOperation("新增消息")
    public Response<String> addMessage(@RequestBody Message message){
        messageService.save(message);
     return Response.with();
    }
    //查询消息
    @GetMapping
    @ApiOperation("获取消息列表")
    public Response<List<Tree<Long>>> listMessage(){
        return Response.with(messageService.listMessage());
    }
    //删除消息
    @DeleteMapping("/{id}")
    @ApiOperation("删除消息")
    public Response<String> deleteMessage(HttpServletRequest request, @PathVariable String id){
        //权限校验 仅管理员有权限删除
        UserInfoDto loginUser = userService.getLoginUser(request);
        if (!loginUser.getRole()){
            throw new CoreException(ErrorCodeEnum.NOT_AUTH_OPTION,"仅管理员有权限删除");
        }
        messageService.removeById(id);
        return Response.with();
    }
}
