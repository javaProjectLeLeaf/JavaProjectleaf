package com.rzspider.implementspider.blogmove.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileOtherConstant;
import com.rzspider.common.utils.DateUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.project.blog.blogcontent.domain.Blogcontent;
import com.rzspider.project.blog.blogcontent.domain.Blogmove;
import com.rzspider.project.common.file.utilt.FileUtils;

/**
 * @author ricozhou
 * @date Oct 17, 2018 12:51:52 PM
 * @Desc
 */
public class BlogMoveOsChinaUtils {

	/**
	 * @date Oct 17, 2018 1:10:19 PM
	 * @Desc 获取标题
	 * @param doc
	 * @return
	 */
	public static String getOsChinaArticleTitle(Document doc) {
		// 标题
		Element pageMsg2 = doc.select("div.article-detail").first().select("h1.header").first();
		return pageMsg2.ownText();
	}

	/**
	 * @date Oct 17, 2018 1:10:28 PM
	 * @Desc 获取作者
	 * @param doc
	 * @return
	 */
	public static String getOsChinaArticleAuthor(Document doc) {
		Element pageMsg2 = doc.select("div.article-detail").first().select("a.__user").first().select("span").first();
		return pageMsg2.html();
	}

	/**
	 * @date Oct 17, 2018 1:10:33 PM
	 * @Desc 获取时间
	 * @param doc
	 * @return
	 */
	public static Date getOsChinaArticleTime(Document doc) {
		Element pageMsg2 = doc.select("div.article-detail").first().select("div.item").first();
		String date = pageMsg2.ownText().trim();
		if (date.startsWith("发布于")) {
			date = date.substring(date.indexOf("发布于") + 3).trim();
		}
		if (date.indexOf(CommonSymbolicConstant.FORWARD_SLASH) < 4) {
			date = DateUtils.format(new Date(), DateUtils.YYYY) + CommonSymbolicConstant.FORWARD_SLASH + date;
		}
		// 这地方时间格式变化太多暂时不实现
		Date d = DateUtils.formatStringDate(date, DateUtils.YYYY_MM_DD_HH_MM_SS3);
		// 注意有些格式不正确
		return d == null ? new Date() : d;
	}

	/**
	 * @date Oct 17, 2018 1:10:37 PM
	 * @Desc 获取类型
	 * @param doc
	 * @param moveArticleType
	 * @return
	 */
	public static String getOsChinaArticleType(Document doc, String moveArticleType) {
		if ("默认".equals(moveArticleType)) {
			Element pageMsg2 = doc.select("div.article-detail").first().select("h1.header").first()
					.select("div.horizontal").first();
			if ("原".equals(pageMsg2.html())) {
				return "原创";
			} else if ("转".equals(pageMsg2.html())) {
				return "转载";
			} else if ("译".equals(pageMsg2.html())) {
				return "翻译";
			}
			return "原创";
		}
		return moveArticleType;
	}

	/**
	 * @date Oct 17, 2018 1:10:41 PM
	 * @Desc 获取正文
	 * @param doc
	 * @param object
	 * @param blogcontent
	 * @return
	 */
	public static String getOsChinaArticleContent(Document doc, Blogmove blogMove, Blogcontent blogcontent) {
		Element pageMsg2 = doc.select("div.content#articleContent").first();
		// 为了图片显示正常去掉一个元素
		pageMsg2.select("div.ad-wrap").remove();
		String content = pageMsg2.toString();
		String images;
		// 注意是否需要替换图片
		if (blogMove.getMoveSaveImg() == 0) {
			// 保存图片到本地
			// 先获取所有图片连接，再按照每个链接下载图片，最后替换原有链接
			// 先创建一个文件夹
			// 先创建一个临时文件夹
			String blogFileName = String.valueOf(UUID.randomUUID());
			FileUtils.createFolder(FilePathConfig.getUploadBlogPath() + File.separator + blogFileName);
			blogcontent.setBlogFileName(blogFileName);
			// 匹配出所有链接
			List<String> imgList = BlogMoveCommonUtils.getArticleImgList(content);
			// 下载并返回重新生成的imgurllist
			List<String> newImgList = getOsChinaArticleNewImgList(blogMove, imgList, blogFileName);
			// 拼接文章所有链接
			images = BlogMoveCommonUtils.getArticleImages(newImgList);
			blogcontent.setImages(images);
			// 替换所有链接按顺序
			content = getOsChinaNewArticleContent(content, imgList, newImgList);

		}

		return content;
	}

	/**
	 * @date Oct 22, 2018 3:31:40 PM
	 * @Desc
	 * @param content
	 * @param imgList
	 * @param newImgList
	 * @return
	 */
	private static String getOsChinaNewArticleContent(String content, List<String> imgList, List<String> newImgList) {
		Document doc = Jsoup.parse(content);
		Elements imgTags = doc.select("img[src]");
		if (imgList == null || imgList.size() < 1 || newImgList == null || newImgList.size() < 1 || imgTags == null
				|| "".equals(imgTags)) {
			return content;
		}
		for (int i = 0; i < imgTags.size(); i++) {
			imgTags.get(i).attr("src", newImgList.get(i));
		}
		return doc.body().toString();
	}

	/**
	 * @date Oct 22, 2018 3:31:33 PM
	 * @Desc
	 * @param imgList
	 * @return
	 */
	private static List<String> getOsChinaArticleNewImgList(Blogmove blogMove, List<String> imgList,
			String blogFileName) {
		// 下载图片
		if (imgList == null || imgList.size() < 1) {
			return null;
		}
		List<String> newImgList = new ArrayList<String>();
		String uuid;
		String filePath = FilePathConfig.getUploadBlogPath() + File.separator + blogFileName;
		String fileName;
		for (String url : imgList) {
			uuid = String.valueOf(UUID.randomUUID());
			fileName = BlogMoveCommonUtils.downloadImg(url, uuid, filePath, blogMove);
			// 打水印
			if (blogMove.getBlogset().getBasicsetAddWaterMark() == 0 && blogMove.getMoveAddWaterMark() == 0
					&& blogMove.getBlogset().getBasicsetWaterMarkMsg() != null
					&& !CommonSymbolicConstant.EMPTY_STRING.equals(blogMove.getBlogset().getBasicsetWaterMarkMsg())) {
				BlogMoveCommonUtils.addImgWaterMark(blogFileName, fileName, blogMove);
			}

			newImgList.add(FileOtherConstant.FILE_JUMP_PATH_PREFIX3 + blogFileName + File.separator + fileName);
		}

		return newImgList;
	}

}
