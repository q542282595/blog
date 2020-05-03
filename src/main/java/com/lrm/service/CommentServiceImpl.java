package com.lrm.service;

import com.lrm.dao.CommentRepository;
import com.lrm.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    private List<Comment> tempReplys=new ArrayList<>();
    @Override
    public List<Comment> listCommentByBlogId(Long id) {
        Sort sort=new Sort("createTime");
        List<Comment> comments=commentRepository.findByBlogIdAndParentCommentNull(id,sort);
        return eachComment(comments);
    }

    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentView=new ArrayList<>();
        for (Comment comment : comments) {
            Comment c=new Comment();
            BeanUtils.copyProperties(comment,c);
            commentView.add(c);
        }
        combineChildren(commentView);
        return commentView;
    }

    private void combineChildren(List<Comment> comments) {
        for (Comment comment : comments) {
            List<Comment> replys1=comment.getReplyComment();
            for (Comment reply1 : replys1) {
                recursively(reply1);
            }
            comment.setReplyComment(tempReplys);
            tempReplys=new ArrayList<>();
        }
    }

    private void recursively(Comment comment) {
        tempReplys.add(comment);
        if(comment.getReplyComment().size()>0)
        {
            List<Comment> replys=comment.getReplyComment();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if(reply.getReplyComment().size()>0)
                {
                    recursively(reply);
                }
            }
        }

    }

    @Override
    public Comment saveComment(Comment  comment) {
        Long parentCommentId=comment.getParentComment().getId();
        if(parentCommentId!=-1)
        {
            comment.setParentComment(commentRepository.findById(parentCommentId).get());
        }else
        {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
}
