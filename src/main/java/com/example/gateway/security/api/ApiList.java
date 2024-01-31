package com.example.gateway.security.api;

public abstract class ApiList {

    private static final String[] blackList = {
            "/api/review"
    };
    private static final String[] ownerList = {

    };
    public static String[] getBlackList() {
        return blackList;
    }
    public static String[] getOwnerList() {
        return ownerList;
    }
}
