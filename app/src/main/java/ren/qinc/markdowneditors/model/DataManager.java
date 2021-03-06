/*
 * Copyright 2016. SHENQINCI(沈钦赐)<dev@qinc.me>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ren.qinc.markdowneditors.model;

import android.support.annotation.NonNull;

import org.reactivestreams.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import ren.qinc.markdowneditors.entity.FileBean;
import ren.qinc.markdowneditors.utils.Check;
import ren.qinc.markdowneditors.utils.FileUtils;
import ren.qinc.markdowneditors.utils.RxUtils;

/**
 * Model统一数据管理
 * Created by 沈钦赐 on 16/1/26.
 */
public class DataManager {
    private IFileModel mFileModel;

    public static DataManager getInstance() {
        return DataManagerInstance.getManager();
    }

    private DataManager() {
        mFileModel = FileModel.getInstance();
    }

    /**
     * 读取文件
     *
     * @param file the file path
     * @return the observable
     */
    public Observable<String> readFile(@NonNull File file) {
        return Observable.create((ObservableOnSubscribe<String>) emitter -> {
            if (file == null) {
                emitter.onError(new IllegalStateException("文件获取失败：路径错误"));
                return;
            }
            if (file.isDirectory()) {
                emitter.onError(new IllegalStateException("文件获取失败：不是文件"));
                return;
            }
            if (!file.exists()) {
                emitter.onError(new IllegalStateException("文件获取失败：文件不存在"));
                return;
            }

            emitter.onNext(FileUtils.readFile(file));
        })
        .compose(RxUtils.applySchedulersIoAndMainThread());
    }

    /**
     * 保存文件
     *
     * @param file    the file path
     * @param content the content
     * @return the observable
     */
    public Observable<Boolean> saveFile(@NonNull File file, @NonNull String content) {
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                if (file == null) {
//                    subscriber.onError(new IllegalStateException("文件保存失败：路径错误"));
//                    return;
//                }
//
//                boolean b = FileUtils.writeByte(file, content);
//                subscriber.onNext(b);
//            }
//        }).compose(RxUtils.applySchedulersIoAndMainThread());

        return Observable.just(true);
    }

    /**
     * 获取文件列表，（md文件和文件夹）并转化为FileBean
     * Gets file list data.
     *
     * @param currentFolder the current folder
     * @return the file list data
     */
    public Observable<List<FileBean>> getFileListData(File currentFolder, String key) {
//        File[] files = null;
//        if (Check.isEmpty(key))//默认，文件夹和文件
//            files = currentFolder
//                    .listFiles(file -> file.isDirectory() ||
//                            file.getName().endsWith(".md") ||
//                            file.getName().endsWith(".markdown") ||
//                            file.getName().endsWith(".mdown"));
//        else //搜索
//            files = currentFolder
//                    .listFiles(file -> file.getName().contains(key) &&
//                            (
//                                    file.getName().endsWith(".md") ||
//                                            file.getName().endsWith(".markdown") ||
//                                            file.getName().endsWith(".mdown")));//只显示md和文件夹
//
//
//        if (files == null)
//            return getCommonObservable();
//
//        return Observable
//                .from(files)
//                .filter(file -> file != null)
////                .filter(file -> file.isDirectory() || file.getName().endsWith(".md"))
//                .flatMap(file -> mFileModel.getFileBeanObservable(file)
//                        .filter(bean -> bean != null))
////                .toList()
//                .toSortedList(this::fileSort)
//                .compose(RxUtils.applySchedulersIoAndMainThread());
        return Observable.just(new ArrayList<>());
    }

    /**
     * 文件复制
     * Copy file observable.
     *
     * @param beans      the beans 待复制的文件或者文件夹集合
     * @param targetPath the targetPath 目标目录
     * @return the observable
     */
    public Observable<FileBean> copyFile(List<FileBean> beans, String targetPath) {
//        return Observable
//                .from(beans)
////                .flatMap(bean -> mFileModel.getFileObservable(bean))
//                .map(bean ->
//                        FileUtils.copyFolder(bean.absPath, targetPath) ? bean : null
//                )
//                .map(bean -> {
//                    if (bean == null) throw new IllegalStateException("复制失败了");
//                    else return bean;
//                })
//                .map(bean -> {//新路径改变
//                    if (targetPath.endsWith(File.separator)) {
//                        bean.absPath = targetPath + bean.name;
//                    } else {
//                        bean.absPath = targetPath + File.separator + bean.name;
//                    }
//                    return bean;
//                })
//                .compose(RxUtils.applySchedulersIoAndMainThread());
        return Observable.empty();
    }

    public Observable<FileBean> cutFile(List<FileBean> beans, String target) {
//        return Observable
//                .from(beans)
////                .flatMap(bean -> mFileModel.getFileObservable(bean))
//                .map(bean ->
//                        FileUtils.moveFolder(bean.absPath, target) ? bean : null
//                )
//                .map(bean -> {
//                    if (bean == null) throw new IllegalStateException("剪切失败了");
//                    else return bean;
//                })
//                .map(bean -> {//新路径改变
//                    if (target.endsWith(File.separator)) {
//                        bean.absPath = target + bean.name;
//                    } else {
//                        bean.absPath = target + File.separator + bean.name;
//                    }
//                    return bean;
//                })
//                .compose(RxUtils.applySchedulersIoAndMainThread());

        return Observable.empty();
    }

    /**
     * 过去一个数据空回调
     * Gets common observable.
     *
     * @param <T> the type parameter
     * @return the common observable
     */
    @SuppressWarnings("unchecked")
    private <T> Observable<T> getCommonObservable() {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                emitter.onNext((T) getNullList());
                emitter.onComplete();
            }
        });
    }

    /**
     * 获取一个空的List
     * Gets null list.
     *
     * @param <T> the type parameter
     * @return the null list
     */
    @SuppressWarnings("unchecked")
    private <T> T getNullList() {
        return (T) new ArrayList<>();
    }

    /**
     * 文件排序
     * File sort int.
     *
     * @param file1 the file 1
     * @param file2 the file 2
     * @return the int
     */
    private int fileSort(FileBean file1, FileBean file2) {
        //大体按照时间排序
        if ((file1.isDirectory && file2.isDirectory) || (!file1.isDirectory && !file2.isDirectory)) {
            return file1.name.compareTo(file2.name);
//            return -1 * file1.lastTime.compareTo(file2.lastTime);
        }
        //如果是文件和文件夹，则文件拍在前面
        if (file1.isDirectory && !file2.isDirectory) {
            return 1;
        } else {
            return -1;
        }
    }

    private static class DataManagerInstance {
        public static DataManager manager = new DataManager();

        public static DataManager getManager() {
            return manager;
        }
    }
}
