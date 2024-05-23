package narnew.cellimagingsystem.service;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import narnew.cellimagingsystem.entity.ImageProcessingHistory;
import narnew.cellimagingsystem.entity.Message;
import narnew.cellimagingsystem.mapper.HistoryMapper;
import narnew.cellimagingsystem.mapper.MessageMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 反馈
 *
 * @author narnew
 * @since 2024年05月12日
 */
@Service
public class MessageService extends ServiceImpl<MessageMapper, Message> {
    public List<Tree<Long>> listMessage() {
        List<Tree<Long>> resList;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //查询所有
        List<Message> data = list();
        //组成树
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setChildrenKey("sons");
        resList = TreeUtil.build(data, 0L, treeNodeConfig, (qualityNode, treeNode) -> {
            treeNode.putExtra("date", formatter.format(qualityNode.getCreatedTime()));
            treeNode.putExtra("content", qualityNode.getContent());
            treeNode.putExtra("to_username", qualityNode.getToUsername());
            treeNode.putExtra("username", qualityNode.getUserName());
            treeNode.setId(qualityNode.getId());
            treeNode.setParentId(qualityNode.getParentId());
        });
        if (!CollectionUtils.isEmpty(resList)) {
            for (Tree<Long> tree : resList) {
                if (StringUtils.isEmpty(tree.get("sons"))) {
                    tree.putExtra("sons",new ArrayList<>());
                }
            }
        }

        return resList==null? new ArrayList<>():resList;
    }
}
