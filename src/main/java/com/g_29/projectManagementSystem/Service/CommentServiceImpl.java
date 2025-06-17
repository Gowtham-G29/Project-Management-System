package com.g_29.projectManagementSystem.Service;

import com.g_29.projectManagementSystem.Repository.CommentRepo;
import com.g_29.projectManagementSystem.Repository.IssueRepo;
import com.g_29.projectManagementSystem.Repository.UserRepo;
import com.g_29.projectManagementSystem.model.Comment;
import com.g_29.projectManagementSystem.model.Issue;
import com.g_29.projectManagementSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private IssueRepo issueRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Comment createComment(Long issueId, Long userId, String comment) throws Exception {

        Optional<Issue> issueOptional=issueRepo.findById(issueId);
        Optional<User>userOptional=userRepo.findById(userId);

        if(issueOptional.isEmpty()){
            throw new Exception("issue not found in this Id");
        }
        if(userOptional.isEmpty()){
            throw new Exception("User Not found in this Exception");
        }

        Issue issue=issueOptional.get();
        User user=userOptional.get();

        Comment newComment=new Comment();
        newComment.setIssue(issue);
        newComment.setUser(user);
        newComment.setCreatedDateTime(LocalDateTime.now());
        newComment.setContent(comment);

        Comment savedComment=commentRepo.save(newComment);

        issue.getComments().add(savedComment);

        return savedComment;

    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {

         Optional<Comment>optionalComment=commentRepo.findById(commentId);
         Optional<User>optionalUser=userRepo.findById(userId);

         if(optionalComment.isEmpty()){
             throw new Exception("Comment is not found in this id");
         }

         if(optionalUser.isEmpty()){
             throw new Exception("User is Not found in this Id");
         }

         Comment comment=optionalComment.get();
         User user=optionalUser.get();

          if(comment.getUser().equals(user)){
              commentRepo.delete(comment);
          }else {
              throw new Exception("User doesnt have permission to delete this comment!");
          }
    }

    @Override
    public List<Comment> findCommentByIssueId(Long issueId) throws Exception {
        return commentRepo.findByIssueId(issueId);
    }
}
