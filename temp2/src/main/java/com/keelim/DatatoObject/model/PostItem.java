package com.keelim.DatatoObject.model;

public class PostItem {

    String userName;
    boolean isUserLike;

    int postLikeCount;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isUserLike() {
        return isUserLike;
    }

    public void setUserLike(boolean userLike) {
        isUserLike = userLike;
    }

    public int getPostLikeCount() {
        return postLikeCount;
    }

    public void setPostLikeCount(int postLikeCount) {
        this.postLikeCount = postLikeCount;
    }

    public String getPostImgUrl() {
        return postImgUrl;
    }

    public void setPostImgUrl(String postImgUrl) {
        this.postImgUrl = postImgUrl;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    String postImgUrl;
    String postText;

    public PostItem(String userName, boolean isUserLike, int postLikeCount, String postImgUrl, String postText) {
        this.userName = userName;
        this.isUserLike = isUserLike;
        this.postLikeCount = postLikeCount;
        this.postImgUrl = postImgUrl;
        this.postText = postText;
    }

}
