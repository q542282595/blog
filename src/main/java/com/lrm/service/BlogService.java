package com.lrm.service;

import com.lrm.po.Blog;
import com.lrm.po.Tag;
import com.lrm.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog saveBlog(Blog blog);
    Blog getBlog(Long id);
    Blog getAndConvert(Long id);
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Page<Blog> listBlog(Pageable pageable);
    Page<Blog> listBlog(String query, Pageable pageable);
    Page<Blog> listBlog(Long tagId, Pageable pageable);
    Map<String,List<Blog>> archiveBlog();
    Long countBlog();
    Blog updateBlog(Long id, Blog blog);
    void deleteBlog(Long id);

    List<Blog> listRecommendBlogTop(Integer size);
}
