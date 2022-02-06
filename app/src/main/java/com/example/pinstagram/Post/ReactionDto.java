package com.example.pinstagram.Post;

public class ReactionDto {
    private Long postId;
    private boolean reactionType;
    private String reactionBy;
    private Long timeStamp;

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public boolean isReactionType() {
        return reactionType;
    }

    public void setReactionType(boolean reactionType) {
        this.reactionType = reactionType;
    }

    public String getReactionBy() {
        return reactionBy;
    }

    public void setReactionBy(String reactionBy) {
        this.reactionBy = reactionBy;
    }
}
