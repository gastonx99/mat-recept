package se.dandel.recipe.web.infra;

import java.io.Serializable;

public class Aktor implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String userId;
    private final String nickname;
    private final String email;

    public Aktor(String userId, String nickname, String email) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

}
