package com.gatsby.crm.model;

/**
 * @PACKAGE_NAME: com.gatsby.crm.model
 * @NAME: TreeModule
 * @AUTHOR: Jonah
 * @DATE: 2023/6/9
 */
public class TreeModule {
    private Integer id;
    private Integer pId;
    private String name;
    private boolean checked = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
