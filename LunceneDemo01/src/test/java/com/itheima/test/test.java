package com.itheima.test;


import java.io.File;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class test {
	@Test
	public void indexWriter() throws Exception {
		//打开索引库
				Directory d = FSDirectory.open(Paths.get("C:\\luence\\index"));
				//指定分词器：标准分词器
				IndexWriterConfig conf = new IndexWriterConfig(new StandardAnalyzer());
				//提供一个写入索引库的对象
				IndexWriter indexWriter = new IndexWriter(d, conf);
				
				//得到数据源所在目录
				File sourceFile = new File("C:\\luence\\source");
				//得到数据源目录中所有文件
				File[] files = sourceFile.listFiles();
				//遍历
				for (File f : files) {
					String fname = f.getName();//文件标题
					String fcontent = FileUtils.readFileToString(f);//文件内容
					String fpath = f.getPath();//文件的路径
					long fsize = FileUtils.sizeOf(f);//文件的大小
					
					//参数一：域字段名。参数二：解析的具体值。参数三：是否存储。
					Field nameField = new TextField("fname", fname, Store.YES);
					Field contentField = new TextField("fcontent", fcontent, Store.YES);
					Field pathField = new StoredField("fpath", fpath);
					Field sizeField = new StoredField("fsize", fsize);
					
					//创建文档
					Document document = new Document();
					document.add(sizeField);
					document.add(pathField);
					document.add(contentField);
					document.add(nameField);
					
					//把文档写入到索引库
					indexWriter.addDocument(document);
				}
				//关闭资源
				indexWriter.close();
	}
}
