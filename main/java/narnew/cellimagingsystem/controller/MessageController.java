package narnew.cellimagingsystem.controller;

import cn.hutool.core.lang.tree.Tree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import narnew.cellimagingsystem.base.Response;
import narnew.cellimagingsystem.entity.Message;
import narnew.cellimagingsystem.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    //新增消息
    @PostMapping
    @ApiOperation("新增消息")
    public Response addMessage(@RequestBody Message message){
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
    public Response deleteMessage(@PathVariable String id){
        messageService.removeById(id);
        return Response.with();
    }
}
