package com.rzspider.project.book.bookmanage.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.rzspider.project.yd.ydManage.domain.YwInfo;
import com.rzspider.project.yw.ywManage.domain.YwInfoList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.project.book.bookmanage.domain.Bookmanage;

public class ExcelUtils {

	// 解析excel工具类
	public static List<Bookmanage> readExcel(File file) {
		List<Bookmanage> bmList = null;
		try {
			bmList = new ArrayList<Bookmanage>();
			Bookmanage bm = null;
			Workbook wb = null;
			Sheet sheet = null;
			Row row = null;
			String cellData = null;
			// 获取工作簿
			wb = getWorkbook(file);
			if (wb != null) {
				// 获取第一个sheet
				sheet = (Sheet) wb.getSheetAt(0);
				// 获取最大行数
				int rownum = sheet.getPhysicalNumberOfRows();
				// 获取第一行
				row = sheet.getRow(0);
				// 获取最大列数
				int colnum = row.getPhysicalNumberOfCells();
				// 从第二行第二列开始读取
				for (int i = 1; i < rownum; i++) {
					bm = new Bookmanage();
					row = sheet.getRow(i);
					if (row != null) {

						// 图书名称
						if (!"".equals(row.getCell(1))) {
							bm.setBookName(row.getCell(1).toString());
						}
						bm.setBookAuthor(row.getCell(2).toString());
						bm.setBookPublisher(row.getCell(3).toString());
						bm.setBookIsbn(row.getCell(4).toString());
						bm.setBookDes(row.getCell(5).toString());
						bm.setBookLanguage(row.getCell(6).toString());
						bm.setBookOriginalPrice(row.getCell(7).toString());
						bm.setBookPurchasePrice(row.getCell(8).toString());
						bm.setBookPublishTime(row.getCell(9).toString());
						bm.setBookClassification(row.getCell(10).toString());
						bm.setBookBinding(row.getCell(11).toString());
						if (!"".equals(row.getCell(12).toString())) {
							bm.setBookPages(Double.valueOf(row.getCell(12).toString()).intValue());
						}
						bm.setBookStayAddress(row.getCell(13).toString());
						if (row.getCell(14).toString().contains("已读") || "0.0".equals(row.getCell(14).toString())
								|| "0".equals(row.getCell(14).toString())) {
							// 已读
							bm.setBookReadStatus(0);
						} else if (row.getCell(14).toString().contains("未读") || "1.0".equals(row.getCell(14).toString())
								|| "1".equals(row.getCell(14).toString())) {
							// 未读
							bm.setBookReadStatus(1);
						} else if (row.getCell(14).toString().contains("阅读中")
								|| "2.0".equals(row.getCell(14).toString()) || "2".equals(row.getCell(14).toString())) {
							// 阅读中
							bm.setBookReadStatus(2);
						} else {
							// 未读
							bm.setBookReadStatus(1);
						}
						if (!"".equals(row.getCell(15).toString())) {
							if (Double.valueOf(row.getCell(15).toString()).intValue() > 5) {
								bm.setBookReadStar(5);
							} else {
								bm.setBookReadStar(Double.valueOf(row.getCell(15).toString()).intValue());
							}
						}

						bm.setBookReadEvaluation(row.getCell(16).toString());

						bm.setUserId(Integer.valueOf(String.valueOf(ShiroUtils.getUserId())));
						if (row.getCell(17).toString().contains("不") || row.getCell(17).toString().contains("否")
								|| "1.0".equals(row.getCell(17).toString())) {
							// 不在
							bm.setStatus(1);
						} else if (row.getCell(17).toString().contains("在") || row.getCell(17).toString().contains("是")
								|| "0.0".equals(row.getCell(17).toString())) {
							// 在
							bm.setStatus(0);
						} else {
							bm.setStatus(0);
						}
						bm.setCreateBy(ShiroUtils.getLoginName());
						bm.setRemark(row.getCell(18).toString());
					} else {
						break;
					}
					bmList.add(bm);
				}
			}
			return bmList;
		} catch (Exception e) {
			e.printStackTrace();
			return bmList;
		}

	}
//读取业务excel文件
	public static List<YwInfoList> readYwExcel(File file) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy", Locale.US);

		List<YwInfoList> ywInfoLists = null;
		try {
			ywInfoLists = new ArrayList<YwInfoList>();
			YwInfoList bm = null;
			Workbook wb = null;
			Sheet sheet = null;
			Row row = null;
			String cellData = null;
			// 获取工作簿
			wb = getWorkbook(file);
			if (wb != null) {
				// 获取第一个sheet
				sheet = (Sheet) wb.getSheetAt(0);
				// 获取最大行数
				int rownum = sheet.getPhysicalNumberOfRows();
				// 获取第一行
				row = sheet.getRow(0);
				// 获取最大列数
				int colnum = row.getPhysicalNumberOfCells();
				// 从第二行第二列开始读取
				for (int i = 1; i < rownum; i++) {
					bm = new YwInfoList();
					row = sheet.getRow(i);
					if (row != null) {

						// 图书名称
						if (!"".equals(row.getCell(1))) {
							bm.setYw_title(row.getCell(1).toString());
						}

						bm.setYw_type(row.getCell(2).toString());
						bm.setYw_dtl(row.getCell(3).toString());
						bm.setYw_content(row.getCell(4).toString());
						bm.setOp_id(Integer.valueOf(row.getCell(5).toString()));
						bm.setStaff_name(row.getCell(6).toString());
						bm.setBill_id(Integer.valueOf(row.getCell(7).toString().substring(0,row.getCell(7).toString().length()-2)));
						bm.setCreate_date(simpleDateFormat.parse(row.getCell(8).toString()));
						bm.setUpdate_date(simpleDateFormat.parse(row.getCell(9).toString()));
						bm.setDelete_date(simpleDateFormat.parse(row.getCell(10).toString()));
						bm.setRestore_date(simpleDateFormat.parse(row.getCell(11).toString()));
						if (row.getCell(12).toString().contains("下线") || "0.0".equals(row.getCell(12).toString())
								|| "0".equals(row.getCell(12).toString())) {
							// 已读
							bm.setStatus(0);
						} else if (row.getCell(12).toString().contains("正常") || "1.0".equals(row.getCell(12).toString())
								|| "1".equals(row.getCell(12).toString())) {
							// 未读
							bm.setStatus(1);

							ywInfoLists.add(bm);
						}
					}
					return ywInfoLists;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ywInfoLists;
		}
		return ywInfoLists;
	}

	//读取移动业务excel文件
	public static List<YwInfo> readYdExcel(File file) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy", Locale.US);

		List<YwInfo> ywInfos = null;
		try {
			ywInfos = new ArrayList<YwInfo>();
			YwInfo bm = null;
			Workbook wb = null;
			Sheet sheet = null;
			Row row = null;
			String cellData = null;
			// 获取工作簿
			wb = getWorkbook(file);
			if (wb != null) {
				// 获取第一个sheet
				sheet = (Sheet) wb.getSheetAt(0);
				// 获取最大行数
				int rownum = sheet.getPhysicalNumberOfRows();
				// 获取第一行
				row = sheet.getRow(0);
				// 获取最大列数
				int colnum = row.getPhysicalNumberOfCells();
				// 从第二行第二列开始读取
				for (int i = 1; i < rownum; i++) {
					bm = new YwInfo();
					row = sheet.getRow(i);
					if (row != null) {

						// 图书名称
						if (!"".equals(row.getCell(1))) {
							bm.setYwTitle(row.getCell(1).toString());
						}

						bm.setYwType(row.getCell(2).toString());
						bm.setYwDtl(row.getCell(3).toString());
						bm.setYwContent(row.getCell(4).toString());
						bm.setStaffName(row.getCell(5).toString());
							ywInfos.add(bm);
						}
					}
					return ywInfos;
				}

		} catch (Exception e) {
			e.printStackTrace();
			return ywInfos;
		}
		return ywInfos;
	}
	// 读取excel返回workbook
	public static Workbook getWorkbook(File file) {
		Workbook wb = null;
		String extString = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			if (".xls".equals(extString)) {
				return wb = new HSSFWorkbook(is);
			} else if (".xlsx".equals(extString)) {
				return wb = new XSSFWorkbook(is);
			} else {
				return wb = null;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return wb;
	}

	// 获取workbook
	public static XSSFWorkbook createExcelFile() {
		String[] title = { "序号", "图书名称", "图书作者", "图书出版社", "图书ISBN", "图书简介", "图书语言", "图书定价", "图书购买价", "图书出版日期", "图书分类",
				"图书装帧", "图书页数", "图书所在地", "阅读状态", "阅读星级", "阅读评价", "在馆状态", "备注" };
		// 创建Excel工作
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建一个sheet表
		XSSFSheet sheet = workbook.createSheet();
		// 设置列宽
		sheet.setColumnWidth(1, 40 * 256);
		sheet.setColumnWidth(2, 30 * 256);
		sheet.setColumnWidth(3, 40 * 256);
		sheet.setColumnWidth(4, 30 * 256);
		sheet.setColumnWidth(5, 50 * 256);
		sheet.setColumnWidth(9, 30 * 256);
		sheet.setColumnWidth(10, 30 * 256);
		sheet.setColumnWidth(16, 50 * 256);
		sheet.setColumnWidth(18, 50 * 256);
		// 创建第一行
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell;
		// 插入第一行数据
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		// 追加数据,向第二行开始加入数据 i = 1
		for (int i = 1; i < 4; i++) {
			XSSFRow row2 = sheet.createRow(i);
			XSSFCell cell2 = row2.createCell(0);
			cell2.setCellValue(i);
			if (i == 1) {
				cell2 = row2.createCell(1);
				cell2.setCellValue("万历十五年");
				cell2 = row2.createCell(2);
				cell2.setCellValue("黄仁宇");
				cell2 = row2.createCell(3);
				cell2.setCellValue("生活·读书·新知三联书店");
				cell2 = row2.createCell(4);
				cell2.setCellValue("0123456789123");
				cell2 = row2.createCell(5);
				cell2.setCellValue("讲述明朝万历年间的故事");
				cell2 = row2.createCell(6);
				cell2.setCellValue("中文");
				cell2 = row2.createCell(7);
				cell2.setCellValue("18.00");
				cell2 = row2.createCell(8);
				cell2.setCellValue("15");
				cell2 = row2.createCell(9);
				cell2.setCellValue("1997-05-01");
				cell2 = row2.createCell(10);
				cell2.setCellValue("历史地理");
				cell2 = row2.createCell(11);
				cell2.setCellValue("平装");
				cell2 = row2.createCell(12);
				cell2.setCellValue("210");
				cell2 = row2.createCell(13);
				cell2.setCellValue("徐州");
				cell2 = row2.createCell(14);
				cell2.setCellValue("0");
				cell2 = row2.createCell(15);
				cell2.setCellValue("5");
				cell2 = row2.createCell(16);
				cell2.setCellValue("非常不错");
				cell2 = row2.createCell(17);
				cell2.setCellValue("0");
				cell2 = row2.createCell(18);
				cell2.setCellValue("无");
			} else if (i == 2) {
				cell2 = row2.createCell(1);
				cell2.setCellValue("做书");
				cell2 = row2.createCell(2);
				cell2.setCellValue("李昕");
				cell2 = row2.createCell(3);
				cell2.setCellValue("商务印书馆");
				cell2 = row2.createCell(4);
				cell2.setCellValue("0123456789124");
				cell2 = row2.createCell(5);
				cell2.setCellValue("讲述作者身为图书编辑的故事");
				cell2 = row2.createCell(6);
				cell2.setCellValue("中文");
				cell2 = row2.createCell(7);
				cell2.setCellValue("39.80");
				cell2 = row2.createCell(8);
				cell2.setCellValue("25.5");
				cell2 = row2.createCell(9);
				cell2.setCellValue("2015-10");
				cell2 = row2.createCell(10);
				cell2.setCellValue("科教文体");
				cell2 = row2.createCell(11);
				cell2.setCellValue("软精装");
				cell2 = row2.createCell(12);
				cell2.setCellValue("340");
				cell2 = row2.createCell(13);
				cell2.setCellValue("徐州");
				cell2 = row2.createCell(14);
				cell2.setCellValue("1");
				cell2 = row2.createCell(15);
				cell2.setCellValue("4");
				cell2 = row2.createCell(16);
				cell2.setCellValue("相当不错");
				cell2 = row2.createCell(17);
				cell2.setCellValue("1");
				cell2 = row2.createCell(18);
				cell2.setCellValue("无");
			} else if (i == 3) {
				cell2 = row2.createCell(1);
				cell2.setCellValue("和平与战争");
				cell2 = row2.createCell(2);
				cell2.setCellValue("【法】雷蒙·阿隆");
				cell2 = row2.createCell(3);
				cell2.setCellValue("中央编译出版社");
				cell2 = row2.createCell(4);
				cell2.setCellValue("0123456789125");
				cell2 = row2.createCell(5);
				cell2.setCellValue("国际关系理论");
				cell2 = row2.createCell(6);
				cell2.setCellValue("中文");
				cell2 = row2.createCell(7);
				cell2.setCellValue("168.00");
				cell2 = row2.createCell(8);
				cell2.setCellValue("85");
				cell2 = row2.createCell(9);
				cell2.setCellValue("2013-6");
				cell2 = row2.createCell(10);
				cell2.setCellValue("政治法律");
				cell2 = row2.createCell(11);
				cell2.setCellValue("精装");
				cell2 = row2.createCell(12);
				cell2.setCellValue("880");
				cell2 = row2.createCell(13);
				cell2.setCellValue("苏州");
				cell2 = row2.createCell(14);
				cell2.setCellValue("2");
				cell2 = row2.createCell(15);
				cell2.setCellValue("5");
				cell2 = row2.createCell(16);
				cell2.setCellValue("相当不错");
				cell2 = row2.createCell(17);
				cell2.setCellValue("0");
				cell2 = row2.createCell(18);
				cell2.setCellValue("无");
			}
		}
		// 写入文件
		return workbook;
	}

	// 获取workbook
	public static XSSFWorkbook createYwExcelFile() {
		String[] title = {  "业务标题", "业务大类", "业务明细", "业务内容", "创建人编号", "创建人姓名",
				"创建人联系方式", "创建时间", "更新时间", "下线时间",
				"恢复时间", "状态" };
		// 创建Excel工作
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建一个sheet表
		XSSFSheet sheet = workbook.createSheet();
		// 设置列宽
		sheet.setColumnWidth(1, 20 * 256);
		sheet.setColumnWidth(2, 20 * 256);
		sheet.setColumnWidth(3, 20 * 256);
		sheet.setColumnWidth(4, 80 * 256);
		sheet.setColumnWidth(5, 20 * 256);
		sheet.setColumnWidth(6, 30 * 256);
		sheet.setColumnWidth(7, 30 * 256);
		sheet.setColumnWidth(8, 40 * 256);
		sheet.setColumnWidth(9, 40 * 256);
		sheet.setColumnWidth(10, 40 * 256);
		sheet.setColumnWidth(11, 40 * 256);
		sheet.setColumnWidth(12, 40 * 256);
		// 创建第一行
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell;
		// 插入第一行数据
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		// 追加数据,向第二行开始加入数据 i = 1

			XSSFRow row2 = sheet.createRow(1);
			XSSFCell cell2 = row2.createCell(0);
			cell2.setCellValue(1);
				cell2 = row2.createCell(1);
				cell2.setCellValue("移动业务");
				cell2 = row2.createCell(2);
				cell2.setCellValue("5G终端");
				cell2 = row2.createCell(3);
				cell2.setCellValue("5G终端");
				cell2 = row2.createCell(4);
				cell2.setCellValue("22");
				cell2 = row2.createCell(5);
				cell2.setCellValue("103");
				cell2 = row2.createCell(6);
				cell2.setCellValue("黄旭峰");
				cell2 = row2.createCell(7);
				cell2.setCellValue("1062292863");
				cell2 = row2.createCell(8);
				cell2.setCellValue("Tue Sep 28 10:03:15 CST 2021");
				cell2 = row2.createCell(9);
				cell2.setCellValue("Tue Sep 28 10:03:15 CST 2021");
				cell2 = row2.createCell(10);
				cell2.setCellValue("Tue Sep 28 10:45:17 CST 2021");
				cell2 = row2.createCell(11);
				cell2.setCellValue("Tue Sep 28 10:45:35 CST 2021");
				cell2 = row2.createCell(12);
				cell2.setCellValue("正常");
		// 写入文件
		return workbook;
	}


	// 获取移动业务workbook
	public static XSSFWorkbook createYdExcelFile() {
		String[] title = { "序号", "业务标题", "业务大类", "业务明细", "业务内容", "创建人姓名",};
		// 创建Excel工作
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建一个sheet表
		XSSFSheet sheet = workbook.createSheet();
		// 设置列宽
		sheet.setColumnWidth(0, 20 * 256);
		sheet.setColumnWidth(1, 20 * 256);
		sheet.setColumnWidth(2, 20 * 256);
		sheet.setColumnWidth(3, 20 * 256);
		sheet.setColumnWidth(4, 80 * 256);
		sheet.setColumnWidth(5, 20 * 256);
		// 创建第一行
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell;
		// 插入第一行数据
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		// 追加数据,向第二行开始加入数据 i = 1

		XSSFRow row2 = sheet.createRow(1);
		XSSFCell cell2 = row2.createCell(0);
		cell2.setCellValue(1);
		cell2 = row2.createCell(1);
		cell2.setCellValue("移动业务");
		cell2 = row2.createCell(2);
		cell2.setCellValue("5G终端");
		cell2 = row2.createCell(3);
		cell2.setCellValue("5G终端");
		cell2 = row2.createCell(4);
		cell2.setCellValue("22");
		cell2 = row2.createCell(5);
		cell2.setCellValue("黄旭峰");

		// 写入文件
		return workbook;
	}

	// workbook写入输出流
	public static boolean writeWBToStream(XSSFWorkbook workbook, ByteArrayOutputStream outputStream) {
		try {
			workbook.write(outputStream);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static XSSFWorkbook createExcelFile(List<Bookmanage> bmList) {

		String[] title = { "序号", "图书名称", "图书作者", "图书出版社", "图书ISBN", "图书简介", "图书语言", "图书定价", "图书购买价", "图书出版日期", "图书分类",
				"图书装帧", "图书页数", "图书所在地", "阅读状态", "阅读星级", "阅读评价", "在馆状态", "备注" };
		// 创建Excel工作
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建一个sheet表
		XSSFSheet sheet = workbook.createSheet();
		// 设置列宽
		sheet.setColumnWidth(1, 40 * 256);
		sheet.setColumnWidth(2, 30 * 256);
		sheet.setColumnWidth(3, 40 * 256);
		sheet.setColumnWidth(4, 30 * 256);
		sheet.setColumnWidth(5, 50 * 256);
		sheet.setColumnWidth(9, 30 * 256);
		sheet.setColumnWidth(10, 30 * 256);
		sheet.setColumnWidth(16, 50 * 256);
		sheet.setColumnWidth(18, 50 * 256);
		// 创建第一行
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell;
		// 插入第一行数据
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		// 追加数据,向第二行开始加入数据 i = 1
		for (int i = 1; i < bmList.size() + 1; i++) {
			XSSFRow row2 = sheet.createRow(i);
			XSSFCell cell2 = row2.createCell(0);
			cell2.setCellValue(i);
			cell2 = row2.createCell(1);
			cell2.setCellValue(bmList.get(i - 1).getBookName());
			cell2 = row2.createCell(2);
			cell2.setCellValue(bmList.get(i - 1).getBookAuthor() != null ? bmList.get(i - 1).getBookAuthor() : "");
			cell2 = row2.createCell(3);
			cell2.setCellValue(
					bmList.get(i - 1).getBookPublisher() != null ? bmList.get(i - 1).getBookPublisher() : "");
			cell2 = row2.createCell(4);
			cell2.setCellValue(bmList.get(i - 1).getBookIsbn() != null ? bmList.get(i - 1).getBookIsbn() : "");
			cell2 = row2.createCell(5);
			cell2.setCellValue(bmList.get(i - 1).getBookDes() != null ? bmList.get(i - 1).getBookDes() : "");
			cell2 = row2.createCell(6);
			cell2.setCellValue(bmList.get(i - 1).getBookLanguage() != null ? bmList.get(i - 1).getBookLanguage() : "");
			cell2 = row2.createCell(7);
			cell2.setCellValue(
					bmList.get(i - 1).getBookOriginalPrice() != null ? bmList.get(i - 1).getBookOriginalPrice() : "");
			cell2 = row2.createCell(8);
			cell2.setCellValue(
					bmList.get(i - 1).getBookPurchasePrice() != null ? bmList.get(i - 1).getBookPurchasePrice() : "");
			cell2 = row2.createCell(9);
			cell2.setCellValue(
					bmList.get(i - 1).getBookPublishTime() != null ? bmList.get(i - 1).getBookPublishTime() : "");
			cell2 = row2.createCell(10);
			cell2.setCellValue(
					bmList.get(i - 1).getBookClassification() != null ? bmList.get(i - 1).getBookClassification() : "");
			cell2 = row2.createCell(11);
			cell2.setCellValue(bmList.get(i - 1).getBookBinding() != null ? bmList.get(i - 1).getBookBinding() : "");
			cell2 = row2.createCell(12);
			cell2.setCellValue(
					String.valueOf(bmList.get(i - 1).getBookPages() != null ? bmList.get(i - 1).getBookPages() : ""));
			cell2 = row2.createCell(13);
			cell2.setCellValue(
					bmList.get(i - 1).getBookStayAddress() != null ? bmList.get(i - 1).getBookStayAddress() : "");
			cell2 = row2.createCell(14);
			cell2.setCellValue(String.valueOf(bmList.get(i - 1).getBookReadStatus()));
			cell2 = row2.createCell(15);
			cell2.setCellValue(String.valueOf(bmList.get(i - 1).getBookReadStar()));
			cell2 = row2.createCell(16);
			cell2.setCellValue(
					bmList.get(i - 1).getBookReadEvaluation() != null ? bmList.get(i - 1).getBookReadEvaluation() : "");
			cell2 = row2.createCell(17);
			cell2.setCellValue(String.valueOf(bmList.get(i - 1).getStatus()));
			cell2 = row2.createCell(18);
			cell2.setCellValue(bmList.get(i - 1).getRemark() != null ? bmList.get(i - 1).getRemark() : "");
		}
		// 写入文件
		return workbook;
	}


	public static XSSFWorkbook createExcelFile1(List<YwInfoList> ywInfoLists) {

		String[] title = { "序号", "图书名称"};
		// 创建Excel工作
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建一个sheet表
		XSSFSheet sheet = workbook.createSheet();
		// 设置列宽
		sheet.setColumnWidth(1, 40 * 256);
		sheet.setColumnWidth(2, 30 * 256);
		// 创建第一行
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell;
		// 插入第一行数据
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		// 追加数据,向第二行开始加入数据 i = 1
		for (int i = 1; i < ywInfoLists.size() + 1; i++) {
			XSSFRow row2 = sheet.createRow(i);
			XSSFCell cell2 = row2.createCell(0);
			cell2.setCellValue(i);
			cell2 = row2.createCell(1);
			cell2.setCellValue(ywInfoLists.get(i - 1).getYw_title());
			cell2 = row2.createCell(2);
			cell2.setCellValue(ywInfoLists.get(i - 1).getYw_title() != null ? ywInfoLists.get(i - 1).getYw_title() : "");
		}
		// 写入文件
		return workbook;
	}

	public static XSSFWorkbook createExcelFileYwInfo(List<YwInfo> ywInfos) {

		String[] title = { "序号","业务标题","业务大类", "业务明细","业务内容"};
		// 创建Excel工作
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建一个sheet表
		XSSFSheet sheet = workbook.createSheet();
		// 设置列宽
		sheet.setColumnWidth(1, 5 * 256);
		sheet.setColumnWidth(2, 15 * 256);
		sheet.setColumnWidth(3, 20 * 256);
		sheet.setColumnWidth(4, 20 * 256);
		sheet.setColumnWidth(5, 40 * 256);
		// 创建第一行
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell;
		// 插入第一行数据
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		// 追加数据,向第二行开始加入数据 i = 1
		for (int i = 1; i < ywInfos.size() + 1; i++) {
			XSSFRow row2 = sheet.createRow(i);
			XSSFCell cell2 = row2.createCell(0);
			cell2.setCellValue(i);
			cell2 = row2.createCell(1);
			cell2.setCellValue(ywInfos.get(i - 1).getYwTitle());
			cell2 = row2.createCell(2);
			cell2.setCellValue(ywInfos.get(i - 1).getYwType() != null ? ywInfos.get(i - 1).getYwType() : "");
			cell2 = row2.createCell(3);
			cell2.setCellValue(ywInfos.get(i - 1).getYwDtl() != null ? ywInfos.get(i - 1).getYwDtl() : "");
			cell2 = row2.createCell(4);
			cell2.setCellValue(ywInfos.get(i - 1).getYwContent() != null ? ywInfos.get(i - 1).getYwContent() : "");
		}
		// 写入文件
		return workbook;
	}
}
