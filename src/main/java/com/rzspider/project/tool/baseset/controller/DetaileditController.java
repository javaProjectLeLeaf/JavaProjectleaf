package com.rzspider.project.tool.baseset.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.FileMessageConstant;
import com.rzspider.common.constant.FileOtherConstant;
import com.rzspider.common.constant.OtherConstant;
import com.rzspider.common.constant.ReturnMessageConstant;
import com.rzspider.common.constant.project.ToolConstant;
import com.rzspider.common.utils.FileUploadUtils;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.tool.baseset.domain.Baseset;
import com.rzspider.project.tool.baseset.service.IBasesetDetaileditService;
import com.rzspider.project.tool.baseset.service.IBasesetService;
import com.rzspider.project.tool.baseset.utils.BasesetUtils;

/**
 * ???????????????????????? ??????????????????
 * 
 * @author ricozhou
 * @date 2018-06-23
 */
@Controller
@RequestMapping("/tool/baseset/detailedit")
public class DetaileditController extends BaseController {
	private String prefix = "tool/baseset/detailedit";

	@Autowired
	private IBasesetService basesetService;
	@Autowired
	private IBasesetDetaileditService basesetDetaileditService;

	/* java?????????????????? */
	@Log(title = "????????????", action = "????????????-java??????????????????")
	@RequiresPermissions("tool:baseset:detailedit:spiderJavaCodeExampleEdit")
	@GetMapping("/spiderJavaCodeExampleEdit/{basesetId}")
	public String spiderJavaCodeExampleEdit(@PathVariable("basesetId") Integer basesetId, Model model) {
		Baseset baseset = basesetService.selectBasesetById(basesetId);
		model.addAttribute("baseset", baseset);
		return prefix + "/spiderJavaCodeExampleEdit";
	}

	/* python?????????????????? */
	@Log(title = "????????????", action = "????????????-Python??????????????????")
	@RequiresPermissions("tool:baseset:detailedit:spiderPythonCodeExampleEdit")
	@GetMapping("/spiderPythonCodeExampleEdit/{basesetId}")
	public String spiderPythonCodeExampleEdit(@PathVariable("basesetId") Integer basesetId, Model model) {
		Baseset baseset = basesetService.selectBasesetById(basesetId);
		model.addAttribute("baseset", baseset);
		return prefix + "/spiderPythonCodeExampleEdit";
	}

	/* javascript?????????????????? */
	@Log(title = "????????????", action = "????????????-js??????????????????")
	@RequiresPermissions("tool:baseset:detailedit:spiderJavascriptCodeExampleEdit")
	@GetMapping("/spiderJavascriptCodeExampleEdit/{basesetId}")
	public String spiderJavascriptCodeExampleEdit(@PathVariable("basesetId") Integer basesetId, Model model) {
		Baseset baseset = basesetService.selectBasesetById(basesetId);
		model.addAttribute("baseset", baseset);
		return prefix + "/spiderJavascriptCodeExampleEdit";
	}

	/* ?????????????????? */
	@GetMapping("/loginSetEdit/{basesetId}")
	public String loginSetEdit(@PathVariable("basesetId") Integer basesetId, Model model) {
		Baseset baseset = basesetService.selectBasesetById(basesetId);
		model.addAttribute("baseset", baseset);
		return prefix + "/loginSetEdit";
	}

	/* ???????????????????????? */
	@Log(title = "????????????", action = "????????????-??????????????????")
	@GetMapping("/previewLoginbg/{loginbgName}/{loginbgType}")
	public String previewLoginbg(@PathVariable("loginbgName") String loginbgName,
			@PathVariable("loginbgType") Integer loginbgType, Model model) {
		// ??????????????????????????????
		// ??????????????????????????????url
		// ???????????????????????????????????????
		if (loginbgType == 0) {
			if (!CommonSymbolicConstant.NEGATIVE_1.equals(loginbgName)) {
				// ????????????
				loginbgName = URLDecoder.decode(loginbgName);
			} else {
				loginbgName = CommonSymbolicConstant.EMPTY_STRING;
			}
		} else if (loginbgType == 1) {
			loginbgName = FileOtherConstant.FILE_JUMP_PATH_PREFIX + loginbgName;
		}
		model.addAttribute("loginbgType", loginbgType);
		model.addAttribute("loginbgName", loginbgName);
		return prefix + "/previewLoginbg";
	}

	/* ???????????????????????? */
	@Log(title = "????????????", action = "????????????-??????????????????")
	@GetMapping("/homeIntroductionEdit/{basesetId}")
	public String homeIntroductionEdit(@PathVariable("basesetId") Integer basesetId, Model model) {
		Baseset baseset = basesetService.selectBasesetById(basesetId);
		model.addAttribute("baseset", baseset);
		return prefix + "/homeIntroductionEdit";
	}

	/* ???????????????????????? */
	@Log(title = "????????????", action = "????????????-??????????????????")
	@GetMapping("/spiderWebsiteManualEdit/{basesetId}")
	public String spiderWebsiteManualEdit(@PathVariable("basesetId") Integer basesetId, Model model) {
		Baseset baseset = basesetService.selectBasesetById(basesetId);
		model.addAttribute("baseset", baseset);
		return prefix + "/spiderWebsiteManualEdit";
	}

	/* ???????????? */
	@Log(title = "????????????", action = "????????????-????????????")
	@GetMapping("/previewManual/{flag}")
	public String previewManual(@PathVariable("flag") Integer flag, Model model) {
		// ?????????????????????
		Baseset baseset = basesetService.getBaseset();
		String pdfName = CommonSymbolicConstant.EMPTY_STRING;
		if (flag == 0) {
			if (baseset.getSpiderWebsiteManualType() == 1) {
				model.addAttribute("spiderManualType", 1);
				model.addAttribute("spiderManual", baseset.getSpiderWebsiteManual());
			} else if (baseset.getSpiderWebsiteManualType() == 0) {
				// ??????????????????pdf???????????????pdf
				// pdfName = UUID.randomUUID() + ".pdf";
				// if
				// (BasesetUtils.htmlCodeComeString(baseset.getSpiderWebsiteManual(),
				// FilePathConfig.getUploadPath() + File.separator + pdfName)) {
				// model.addAttribute("spiderManualType", 0);
				// model.addAttribute("spiderManual", pdfName);
				// }
				// ??????????????????????????????
				// ????????????
				model.addAttribute("spiderManualType", 0);
				model.addAttribute("spiderManual", baseset.getSpiderWebsiteManual());
			}
		} else if (flag == 1) {
			if (baseset.getSpiderUseManualType() == 1) {
				model.addAttribute("spiderManualType", 1);
				model.addAttribute("spiderManual", baseset.getSpiderUseManual());
			} else if (baseset.getSpiderUseManualType() == 0) {
				// ??????????????????pdf???????????????pdf
				// pdfName = UUID.randomUUID() + ".pdf";
				// if
				// (BasesetUtils.htmlCodeComeString(baseset.getSpiderUseManual(),
				// FilePathConfig.getUploadPath() + File.separator + pdfName)) {
				// model.addAttribute("spiderManualType", 0);
				// model.addAttribute("spiderManual", pdfName);
				// }
				// ??????????????????????????????
				// ????????????
				model.addAttribute("spiderManualType", 0);
				model.addAttribute("spiderManual", baseset.getSpiderUseManual());
			}
		}
		return prefix + "/previewManual";
	}

	/* ???????????? */
	@Log(title = "????????????", action = "????????????-????????????")
	@GetMapping("/downloadManual/{flag}")
	public void downloadManual(@PathVariable("flag") Integer flag, HttpServletResponse response) throws IOException {
		response.reset();
		// ?????????????????????
		Baseset baseset = basesetService.getBaseset();
		String baseName = CommonSymbolicConstant.EMPTY_STRING;
		String fileName = CommonSymbolicConstant.EMPTY_STRING;
		String pdfName = CommonSymbolicConstant.EMPTY_STRING;
		if (flag == 0) {
			baseName = ToolConstant.TOOL_MANUAL_WEBSITEMANUAL_DEFAULT_NAME;
			if (baseset.getSpiderWebsiteManualType() == 1) {
				fileName = baseset.getSpiderWebsiteManual();
			} else if (baseset.getSpiderWebsiteManualType() == 0) {
				pdfName = UUID.randomUUID() + FileExtensionConstant.FILE_EXTENSION_POINT_FILE_PDF;
				if (BasesetUtils.htmlCodeComeString(baseset.getSpiderWebsiteManual(),
						FilePathConfig.getUploadCachePath() + File.separator + pdfName)) {
					fileName = pdfName;
				}
			}
		} else if (flag == 1) {
			baseName = ToolConstant.TOOL_MANUAL_USEMANUAL_DEFAULT_NAME;
			if (baseset.getSpiderUseManualType() == 1) {
				fileName = baseset.getSpiderUseManual();
			} else if (baseset.getSpiderUseManualType() == 0) {
				pdfName = UUID.randomUUID() + FileExtensionConstant.FILE_EXTENSION_POINT_FILE_PDF;
				if (BasesetUtils.htmlCodeComeString(baseset.getSpiderUseManual(),
						FilePathConfig.getUploadCachePath() + File.separator + pdfName)) {
					fileName = pdfName;
				}
			}
		}

		// ??????byte
		byte[] data = basesetService.getPdfToByte(fileName);

		String manualName = (!CommonSymbolicConstant.EMPTY_STRING.equals(basesetService.getDownloadFileNamePrefix())
				? (basesetService.getDownloadFileNamePrefix() + CommonSymbolicConstant.UNDERLINE)
				: CommonSymbolicConstant.EMPTY_STRING) + baseName;
		// ??????????????????
		try {
			manualName = FileUtils.getNewString(manualName);
		} catch (Exception e) {
			e.printStackTrace();
			manualName = baseName;
		}
		response.setHeader(FileMessageConstant.FILE_CONTENT_DISPOSITION,
				FileMessageConstant.FILE_ATTACHMENT_FILENAME + manualName);
		response.addHeader(FileMessageConstant.FILE_CONTENT_LENGTH, CommonSymbolicConstant.EMPTY_STRING + data.length);
		response.setContentType(FileMessageConstant.FILE_CONTENT_TYPE);

		IOUtils.write(data, response.getOutputStream());
		response.getOutputStream().close();
	}

	/* ???????????????????????? */
	@Log(title = "????????????", action = "????????????-??????????????????")
	@GetMapping("/spiderUseManualEdit/{basesetId}")
	public String spiderUseManualEdit(@PathVariable("basesetId") Integer basesetId, Model model) {
		Baseset baseset = basesetService.selectBasesetById(basesetId);
		model.addAttribute("baseset", baseset);
		return prefix + "/spiderUseManualEdit";
	}

	/* ???????????????????????? */
	@GetMapping("/spiderMusicToolEdit/{basesetId}")
	public String spiderMusicToolEdit(@PathVariable("basesetId") Integer basesetId, Model model) {
		model.addAttribute("basesetId", basesetId);
		return prefix + "/musiclist/spiderMusicToolEdit";
	}

	/**
	 * ????????????????????????
	 */
	@Log(title = "????????????", action = "????????????-??????????????????")
	@PostMapping("/loginSetsave")
	@ResponseBody
	public Message loginSetsave(Baseset baseset) {
		if (basesetDetaileditService.loginSetsave(baseset) > 0) {
			return Message.success();
		}
		return Message.error(ToolConstant.TOOL_SAVE_FAILED);
	}

	/**
	 * ??????????????????????????????
	 */
	@Log(title = "????????????", action = "????????????-??????????????????")
	@PostMapping("/homeIntroductionSave")
	@ResponseBody
	public Message homeIntroductionSave(Baseset baseset) {
		if (basesetDetaileditService.homeIntroductionSave(baseset) > 0) {
			return Message.success();
		}
		return Message.error(ToolConstant.TOOL_SAVE_FAILED);
	}

	/**
	 * ????????????????????????
	 */
	@Log(title = "????????????", action = "????????????-??????????????????")
	@RequiresPermissions("tool:baseset:detailedit:spiderJavaCodeExampleEdit")
	@PostMapping("/spiderCodeExampleSave")
	@ResponseBody
	public Message spiderCodeExampleSave(Baseset baseset) {
		if (basesetDetaileditService.spiderCodeExampleSave(baseset) > 0) {
			return Message.success();
		}
		return Message.error(ToolConstant.TOOL_SAVE_FAILED);
	}

	/**
	 * ???????????????????????????????????????????????????
	 */
	@Log(title = "????????????", action = "????????????-????????????")
	@ResponseBody
	@PostMapping("/uploadFile/{basesetId}/{flag}")
	public Message uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			@PathVariable("basesetId") Integer basesetId, @PathVariable("flag") Integer flag) {
		String basePath = FilePathConfig.getUploadCachePath();
		// ?????????????????????????????????
		try {
			FileUploadUtils.assertAllowed(file);
		} catch (FileSizeLimitExceededException e1) {
			e1.printStackTrace();
			return Message.error(FileMessageConstant.FILE_MESSAGE_SIZE_GREATER_TEN_M);
		}
		// ????????????
		String fileName = file.getOriginalFilename();

		if (flag == 0) {
			// java??????
			if (!fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_CODEFILE_JAVA)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_FILE_TXT)) {
				return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
			}
		} else if (flag == 1) {
			// Python??????
			if (!fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_CODEFILE_PY)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_FILE_TXT)) {
				return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
			}
		} else if (flag == 2) {
			// js??????
			if (!fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_CODEFILE_JS)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_FILE_TXT)) {
				return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
			}
		} else if (flag == 3) {
			// ????????????????????????????????????????????????????????????????????????????????????????????????
			basePath = FilePathConfig.getUploadPath();
			// ?????????????????????
			if (!fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_JPG)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_PNG)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_JPEG)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_GIF)) {
				return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
			}
		}

		// ?????????
		fileName = FileUploadUtils.renameToUUID(fileName);
		// ?????????
		try {
			FileUploadUtils.uploadFile(file.getBytes(), basePath, fileName);
		} catch (Exception e) {
			return Message.error();
		}
		// ????????????
		if (flag == 3) {
			// ???????????????Base64??????
			// ??????????????????????????????
			try {
				String imgbase64String = OtherConstant.OTHER_DATAIMAGE
						+ fileName.substring(fileName.lastIndexOf(CommonSymbolicConstant.POINT) + 1)
						+ OtherConstant.OTHER_BASE64 + new String(Base64.encodeBase64(file.getBytes()));

				Message message = new Message();
				message = message.success();
				message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_1, imgbase64String);
				message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_2, fileName);
				return message;
			} catch (IOException e) {
				e.printStackTrace();
				return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_UPLOAD_FAILED);
			}
		}

		// ??????????????????????????????
		String filePath = FilePathConfig.getUploadCachePath() + File.separator + fileName;
		String codeString = FileUtils.getFileToString(filePath).trim();

		return Message.success(codeString);
	}

	/**
	 * ????????????,??????
	 */
	@Log(title = "????????????", action = "????????????-????????????")
	@ResponseBody
	@PostMapping("/uploadManualFile")
	public Message uploadManualFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		// ?????????????????????????????????
		try {
			FileUploadUtils.assertAllowed(file);
		} catch (FileSizeLimitExceededException e1) {
			e1.printStackTrace();
			return Message.error(FileMessageConstant.FILE_MESSAGE_SIZE_GREATER_TEN_M);
		}
		// ????????????
		String fileName = file.getOriginalFilename();
		if (!fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_FILE_PDF)
				&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_FILE_DOC)
				&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_FILE_DOCX)) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
		}
		// ?????????
		fileName = FileUploadUtils.renameToUUID(fileName);
		// ?????????
		try {
			FileUploadUtils.uploadFile(file.getBytes(), FilePathConfig.getUploadPath(), fileName);
			// ???????????????
			return Message.success(fileName);
		} catch (Exception e) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_UPLOAD_FAILED);
		}
	}

	/**
	 * ??????????????????
	 */
	@Log(title = "????????????", action = "????????????-????????????")
	@PostMapping("/spiderManualSave")
	@ResponseBody
	public Message spiderManualSave(Baseset baseset) {
		if (basesetDetaileditService.spiderManualSave(baseset) > 0) {
			return Message.success();
		}
		return Message.error(ToolConstant.TOOL_SAVE_FAILED);
	}

}
