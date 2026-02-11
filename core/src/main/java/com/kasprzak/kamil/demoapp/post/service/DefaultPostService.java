package com.kasprzak.kamil.demoapp.post.service;

import com.kasprzak.kamil.demoapp.post.CommentEntity;
import com.kasprzak.kamil.demoapp.post.CommentRepository;
import com.kasprzak.kamil.demoapp.post.PostEntity;
import com.kasprzak.kamil.demoapp.post.PostRepository;
import com.kasprzak.kamil.demoapp.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class DefaultPostService implements PostService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;


    public void createPost(final long userId, final String content) {
        final var user = userRepository.findById(userId).orElseThrow();
        final var post = new PostEntity(user, content);
        postRepository.save(post);
    }

    public List<PostEntity> getPosts() {
        return postRepository.findAll();
    }

    public List<PostEntity> getUserPosts(Long userId){ return postRepository.findByUserEntity_Id(userId);};

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public void addComment(long postId, long userId, String content) {
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(content);
        commentEntity.setPost(post);

        commentRepository.save(commentEntity);

        post.getCommentEntities().add(commentEntity);
        postRepository.save(post);
    }
}
