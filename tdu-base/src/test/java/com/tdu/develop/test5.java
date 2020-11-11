package com.tdu.develop;

import java.io.*;
import java.util.*;

public class test5 {
    static Map<String, Map<String, File[]>> hashMap = new HashMap<>();
    static class object{
        String name;
        List<object> children;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            name = name;
        }
        public List<object> getChildren() {
            return children;
        }
        public void setChildren(List<object> children) {
            children = children;
        }
    }

    static  List<object> ls = new List<object>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<object> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(object object) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends object> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends object> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public object get(int index) {
            return null;
        }

        @Override
        public object set(int index, object element) {
            return null;
        }

        @Override
        public void add(int index, object element) {

        }

        @Override
        public object remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<object> listIterator() {
            return null;
        }

        @Override
        public ListIterator<object> listIterator(int index) {
            return null;
        }

        @Override
        public List<object> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
    public static void main(String[] args) throws IOException {
        String filepath = "D:\\wamp\\www\\Data\\3D\\Scene\\b971b9bc-18fb-4c2a-982f-7fa991a2a28e\\d2826549-7c84-4fd5-ba10-f4fdf8c8b724";//D盘下的file文件夹的目录
         File file = new File(filepath);//File类型可以是文件也可以是文件夹
        File[] fileList = file.listFiles();//将该目录下的所有文件放置在一个File类型的数组中


        //将该目录下的所有文件放置在一个File类型的数组中

        //Map<String, Map<String, File[]>> map = get(fileList);
        List<object> map = get2(fileList);
        System.out.println(map);


//
//        List<File> wjjList = new ArrayList<File>();//新建一个文件夹集合
//        for (int i = 0; i < fileList.length; i++) {
//            if (fileList[i].isDirectory()) {//判断是否为文件夹
//                wjjList .add(fileList[i]);
//            }
//        }
//
//
 getAllFilePath(file);


    }

    public static List<object> get2(File[] fileList){


        List<File> wjList = new ArrayList<File>();//新建一个文件集合
        for (int i = 0; i < fileList.length; i++) {
            File[] fileLis2t = fileList[i].listFiles();
            if (fileLis2t != null) {
                object object = new object();
                object.setName(fileList[i].getPath());
                //
               // Map<String,File[]> hashMap = new HashMap<String,File[]>();

                List<object> list = new List<object>() {
                    @Override
                    public int size() {
                        return 0;
                    }

                    @Override
                    public boolean isEmpty() {
                        return false;
                    }

                    @Override
                    public boolean contains(Object o) {
                        return false;
                    }

                    @Override
                    public Iterator<test5.object> iterator() {
                        return null;
                    }

                    @Override
                    public Object[] toArray() {
                        return new Object[0];
                    }

                    @Override
                    public <T> T[] toArray(T[] a) {
                        return null;
                    }

                    @Override
                    public boolean add(test5.object object) {
                        return false;
                    }

                    @Override
                    public boolean remove(Object o) {
                        return false;
                    }

                    @Override
                    public boolean containsAll(Collection<?> c) {
                        return false;
                    }

                    @Override
                    public boolean addAll(Collection<? extends test5.object> c) {
                        return false;
                    }

                    @Override
                    public boolean addAll(int index, Collection<? extends test5.object> c) {
                        return false;
                    }

                    @Override
                    public boolean removeAll(Collection<?> c) {
                        return false;
                    }

                    @Override
                    public boolean retainAll(Collection<?> c) {
                        return false;
                    }

                    @Override
                    public void clear() {

                    }

                    @Override
                    public boolean equals(Object o) {
                        return false;
                    }

                    @Override
                    public int hashCode() {
                        return 0;
                    }

                    @Override
                    public test5.object get(int index) {
                        return null;
                    }

                    @Override
                    public test5.object set(int index, test5.object element) {
                        return null;
                    }

                    @Override
                    public void add(int index, test5.object element) {

                    }

                    @Override
                    public test5.object remove(int index) {
                        return null;
                    }

                    @Override
                    public int indexOf(Object o) {
                        return 0;
                    }

                    @Override
                    public int lastIndexOf(Object o) {
                        return 0;
                    }

                    @Override
                    public ListIterator<test5.object> listIterator() {
                        return null;
                    }

                    @Override
                    public ListIterator<test5.object> listIterator(int index) {
                        return null;
                    }

                    @Override
                    public List<test5.object> subList(int fromIndex, int toIndex) {
                        return null;
                    }
                };
                for (int j = 0; j <fileLis2t.length ; j++) {
                    File[] fileLis3t = fileLis2t[j].listFiles();
                    if(fileLis3t!=null){
                        List<object> lis2 = new List<object>() {
                            @Override
                            public int size() {
                                return 0;
                            }

                            @Override
                            public boolean isEmpty() {
                                return false;
                            }

                            @Override
                            public boolean contains(Object o) {
                                return false;
                            }

                            @Override
                            public Iterator<test5.object> iterator() {
                                return null;
                            }

                            @Override
                            public Object[] toArray() {
                                return new Object[0];
                            }

                            @Override
                            public <T> T[] toArray(T[] a) {
                                return null;
                            }

                            @Override
                            public boolean add(test5.object object) {
                                return false;
                            }

                            @Override
                            public boolean remove(Object o) {
                                return false;
                            }

                            @Override
                            public boolean containsAll(Collection<?> c) {
                                return false;
                            }

                            @Override
                            public boolean addAll(Collection<? extends test5.object> c) {
                                return false;
                            }

                            @Override
                            public boolean addAll(int index, Collection<? extends test5.object> c) {
                                return false;
                            }

                            @Override
                            public boolean removeAll(Collection<?> c) {
                                return false;
                            }

                            @Override
                            public boolean retainAll(Collection<?> c) {
                                return false;
                            }

                            @Override
                            public void clear() {

                            }

                            @Override
                            public boolean equals(Object o) {
                                return false;
                            }

                            @Override
                            public int hashCode() {
                                return 0;
                            }

                            @Override
                            public test5.object get(int index) {
                                return null;
                            }

                            @Override
                            public test5.object set(int index, test5.object element) {
                                return null;
                            }

                            @Override
                            public void add(int index, test5.object element) {

                            }

                            @Override
                            public test5.object remove(int index) {
                                return null;
                            }

                            @Override
                            public int indexOf(Object o) {
                                return 0;
                            }

                            @Override
                            public int lastIndexOf(Object o) {
                                return 0;
                            }

                            @Override
                            public ListIterator<test5.object> listIterator() {
                                return null;
                            }

                            @Override
                            public ListIterator<test5.object> listIterator(int index) {
                                return null;
                            }

                            @Override
                            public List<test5.object> subList(int fromIndex, int toIndex) {
                                return null;
                            }
                        };
                        for (File file: fileLis3t) {
                            object object2 = new object();
                            object.setName(fileList[i].getPath());
                            lis2.add(object);
                        }
                        object object3 =    new object();
                        object.setName(fileLis2t[j].getPath());
                        object.setChildren(lis2);
                        list.add(object3);
                        //hashMap.put(""+j+"",fileLis3t);
                    }else{
                        object object3 =    new object();
                        object.setName(fileLis2t[j].getPath());
                        list.add(object3);
                    }
                    fileLis3t =null;
                }
                object.setChildren(list);
                test5.ls.add(object);
                //test5.hashMap.put(""+i+"",hashMap);
            }else{
             object object =    new object();
               object.setName(fileList[i].getPath());
//                Map<String,File[]> hashMap = new HashMap<String,File[]>();
//                File[] fs = new File[1] ;
//                fs[0] =fileList[i];
//                hashMap.put(""+i+"",fs);
//                test5.hashMap.put(""+i+"",hashMap);
                test5.ls.add(object);
            }
            System.out.println(fileList.length);
        }

        return test5.ls;
    }
    public static Map<String, Map<String, File[]>> get(File[] fileList){


        List<File> wjList = new ArrayList<File>();//新建一个文件集合
        for (int i = 0; i < fileList.length; i++) {
            File[] fileLis2t = fileList[i].listFiles();
            if (fileLis2t != null) {

                //
                Map<String,File[]> hashMap = new HashMap<String,File[]>();
                for (int j = 0; j <fileLis2t.length ; j++) {
                    File[] fileLis3t = fileLis2t[j].listFiles();
                    if(fileLis3t!=null){
                        hashMap.put(""+j+"",fileLis3t);
                    }else{
                        File[] fs = new File[1];
                        fs[0] =fileLis2t[j];
                        hashMap.put(""+j+"",fs);
                    }
                    fileLis3t =null;
                }
                test5.hashMap.put(""+i+"",hashMap);
            }else{
                Map<String,File[]> hashMap = new HashMap<String,File[]>();
                File[] fs = new File[1] ;
                fs[0] =fileList[i];
                hashMap.put(""+i+"",fs);
                test5.hashMap.put(""+i+"",hashMap);
            }
            System.out.println(fileList.length);
        }

        return test5.hashMap;
    }

    public static void getAllFilePath(File dir) throws IOException {
        File[] files=dir.listFiles();
        for (File f:files) {
            System.out.println(f.isDirectory()?"文件夹："+f.getAbsolutePath():" 文件："+f.getName());
            if(f.isDirectory()){
                getAllFilePath(f);
            }
        }
//        for(int i=0;i<files.length;i++){
//            if(files[i].isDirectory()){
//                System.out.println(files[i].getPath());
//                //这里面用了递归的算法  
//                getAllFilePath(files[i]);
//
//            } else {
//                System.out.println(files[i].getPath());
//            }
//        }
    }


}
