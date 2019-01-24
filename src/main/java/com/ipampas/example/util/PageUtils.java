package com.ipampas.example.util;


import com.ipampas.framework.model.Page;
import com.ipampas.framework.model.pageable.PageResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * User : liulu
 * Date : 2018/1/3 10:52
 * version $Id: PageUtils.java, v 0.1 Exp $
 */
public class PageUtils {


    public static <T> Page<T> transToPage(List<T> list) {

        Page<T> result = new Page<>();

        result.setPageRecords(list);
        result.setTotalRecords((long) list.size());
        if (list instanceof com.github.pagehelper.Page) {
            com.github.pagehelper.Page page = (com.github.pagehelper.Page) list;
            result.setPageNo(page.getPageNum());
            result.setPageSize(page.getPageSize());
            result.setTotalRecords(page.getTotal());
        }
        return result;
    }


    public static <T, R> PageResult<R> transToPageResult(List<T> list, Function<? super T, ? extends R> mapper) {
        if (null == list || CollectionsTools.isEmpty(list)) {
            return emptyPageResult();
        }
        Page<T> page = transToPage(list);
        PageResult pageResult = new PageResult();
        pageResult.setTotalRecords(page.getTotalRecords());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setPageRecords(list.stream().map(mapper).collect(Collectors.toList()));
        return pageResult;
    }


    public static <T, R> PageResult<R> transToPageResult(List<T> list) {
        if (null == list || CollectionsTools.isEmpty(list)) {
            return emptyPageResult();
        }
        Page<T> page = transToPage(list);
        PageResult pageResult = new PageResult();
        pageResult.setTotalRecords(page.getTotalRecords());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setPageRecords(page.getPageRecords());
        return pageResult;
    }


    public static <T> PageResult<T> emptyPageResult() {
        PageResult pageResult = new PageResult();
        pageResult.setTotalRecords(0L);
        pageResult.setPageRecords(Arrays.asList());
        pageResult.setPageSize(Page.MAX_PAGE_SIZE);
        return pageResult;
    }

    public static <S, T> Page<T> convertToPage(Class<T> targetClass, List<S> sourceList) {

        Page<S> sourcePage = transToPage(sourceList);
        return convertPage(targetClass, sourcePage, null);

    }

    public static <S, T> Page<T> convertToPage(Class<T> targetClass, List<S> sourceList, BeanTools.ConvertCallBack<S, T> convertCallBack) {

        Page<S> sourcePage = transToPage(sourceList);
        return convertPage(targetClass, sourcePage, convertCallBack);

    }


    public static <S, T> Page<T> convertPage(Class<T> targetClass, Page<S> sourcePage) {
        return convertPage(targetClass, sourcePage, null);
    }

    public static <S, T> Page<T> convertPage(Class<T> targetClass, Page<S> sourcePage, BeanTools.ConvertCallBack<S, T> convertCallBack) {
        Page<T> result = new Page<>(sourcePage.getPageNo(), sourcePage.getPageSize());
        result.setTotalRecords(sourcePage.getTotalRecords());

        List<S> sourceRecords = sourcePage.getPageRecords();

        if (sourceRecords == null || sourceRecords.isEmpty()) {
            result.setPageRecords(Collections.emptyList());
        } else {
            result.setPageRecords(BeanTools.convert(targetClass, sourceRecords, convertCallBack));
        }
        return result;
    }

    public static <T> Page<T> emptyPage() {
        Page<T> result = new Page<>();
        result.setPageNo(1);
        result.setTotalRecords(0L);
        result.setPageRecords(Collections.emptyList());
        return result;
    }


}
