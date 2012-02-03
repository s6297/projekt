package org.mpr.jakub.winiarski.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.mpr.jakub.winiarski.exceptions.CommentIsNullException;

@Entity
public class Post implements Serializable {

    private Long id;
    private String subject;
    private String content;
    private String author;
    private Blog blog;
    private List<Comment> comments = new ArrayList<Comment>();
    
    protected Post() {
  
    }

    public Post(String subject) {
        this.subject = subject;
    }
    
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @OneToMany(mappedBy="post")
    public List<Comment> getComments() {
        return comments;
    }
    
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) throws CommentIsNullException {
        if(comment == null) { throw new CommentIsNullException(); }
        comments.add(comment);
        comment.setPost(this);
    }
    
    @ManyToOne
    @JoinColumn(name="blog_fk")
    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
}
