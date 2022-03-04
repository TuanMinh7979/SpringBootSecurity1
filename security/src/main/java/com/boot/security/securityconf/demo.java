package com.boot.security.securityconf;

public class demo {
    public static void main(String args[]) {
        System.out.println(AppUserPermission.COURSE_WRITE.getPermission());
        System.out.println(AppRole.ADMIN.name());
        System.out.println(AppRole.ADMINTRAINEE.getPermission());
    }

}
