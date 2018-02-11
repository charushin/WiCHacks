package com.example.android.spunk;

/**
 * Created by charu on 2/11/2018.
 */

public class Comments {

    public static final String TAG = com.example.android.spunk.PostEntity.class.getSimpleName();

    private int commentId;
    private int userId;
    private int postId;
    private String description;

    public Comments(int commentId, int userId, int postId, String description) {
        this.commentId = commentId;
        this.userId = userId;
        this.postId = postId;
        this.description = description;
    }

    public Comments() {
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
