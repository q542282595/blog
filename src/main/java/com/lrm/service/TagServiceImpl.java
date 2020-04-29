package com.lrm.service;

import com.lrm.NotFoundException;
import com.lrm.dao.TagRepository;
import com.lrm.dao.TypeRepository;
import com.lrm.po.Tag;
import com.lrm.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository tagRepository;

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {

        return tagRepository.save(tag);
    }
    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).get();
    }
    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
      return  tagRepository.findAllById(covertToList(ids));
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=new PageRequest(0,size,sort);
        return tagRepository.findTop(pageable);
    }

    private List<Long> covertToList(String ids)
    {
        List<Long> list=new ArrayList<>();
        if(!"".equals(ids)&&ids!=null)
        {
            String[] idarray = ids.split(",");
            for(int i=0;i<idarray.length;i++)
            {
                list.add(new Long(idarray[i]));
            }
        }

        return list;
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t=tagRepository.findById(id).get();
        if(t==null)
        {
            throw new NotFoundException("标签不存在");
        }
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }
    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);

    }
}
