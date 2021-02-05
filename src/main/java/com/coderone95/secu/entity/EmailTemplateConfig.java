package com.coderone95.secu.entity;

import javax.persistence.*;

@Entity
@Table(name = "tbl_email_template_config")
public class EmailTemplateConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "template_id")
    private Long id;

    @Column(name = "template_name")
    private String templateName;

    @Column(name = "template_for")
    private String templateFor;

    @Column(name = "template")
    private String template;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateFor() {
        return templateFor;
    }

    public void setTemplateFor(String templateFor) {
        this.templateFor = templateFor;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
