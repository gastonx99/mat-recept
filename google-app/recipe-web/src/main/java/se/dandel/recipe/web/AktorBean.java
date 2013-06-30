package se.dandel.recipe.web;

import javax.faces.bean.ManagedBean;

import org.apache.commons.lang.StringUtils;

import se.dandel.recipe.web.infra.AktorManager;

@ManagedBean
public class AktorBean {
    public String getDisplayId() {
        return AktorManager.isInitialized() ? AktorManager.get().getNickname() : StringUtils.EMPTY;
    }
}
