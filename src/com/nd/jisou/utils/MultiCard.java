package com.nd.jisou.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;


/**
 * 澶氬崱瀛樺偍鎺ュ彛绫�
 * @author geolo
 */
public class MultiCard {
	private final long M = 1024 * 1024;
	private final long K = 1024;
	
	/**
	 * 澶栫疆瀛樺偍鍗￠粯璁ら璀︿复鐣屽�
	 */
	private final long DEF_EXTERNAL_SDCARD_WARNNING_LIMIT_SPACE_SIZE = 100 * M;
	
	/**
	 * 鍐呯疆瀛樺偍鍗￠粯璁ら璀︿复鐣屽�
	 */
	private final long DEF_INTERNAL_SDCARD_WARNNING_LIMIT_SPACE_SIZE = 100 * M;
	
	/**
	 * 鎵嬫満榛樿棰勮涓寸晫鍊�
	 */
	private final long DEF_PHONE_WARNNING_LIMIT_SPACE_SIZE = 50 * M;	
	
	/**
	 * 榛樿瑙嗛澶у皬鏈�ぇ鍊�
	 */
	private final long DEF_VIDEO_MAX_SIZE = 50 * M;
	
	/**
	 * 榛樿鍥剧墖澶у皬鏈�ぇ鍊�
	 */
	private final long DEF_IMAGE_MAX_SIZE = 500 * K;
	
	/**
	 * 榛樿鏂囨湰澶у皬鏈�ぇ鍊�
	 */
	private final long DEF_TXT_MAX_SIZE = 300 * K;
	
	/**
	 * 榛樿鏃ュ織澶у皬鏈�ぇ鍊�
	 */
	private final long DEF_LOG_MAX_SIZE = 300 * K;
	
	/**
	 * 榛樿闊抽澶у皬鏈�ぇ鍊�
	 */
	private final long DEF_AUDIO_MAX_SIZE = 10 * M;
	
	/**
	 * 榛樿鑷畾涔夋暟鎹ぇ灏忔渶澶у�
	 */
	private final long DEF_DATA_MAX_SIZE = 50 * M;
	
	/**
	 * 榛樿澶村儚澶у皬鏈�ぇ鍊�
	 */
	private final long DEF_HEAD_MAX_SIZE = 500 * K;
	
	/**
	 * 榛樿APK澶у皬鏈�ぇ鍊�
	 */
	private final long DEF_APK_MAX_SIZE = 20 * M;
	
	
	/**
	 * 濯掍綋绫诲瀷
	 */
	public final static int TYPE_DATA = 0;
	public final static int TYPE_VIDEO = 1;
	public final static int TYPE_IMAGE = 2;
	public final static int TYPE_TXT = 3;
	public final static int TYPE_LOG = 4;
	public final static int TYPE_AUDIO = 5;
	public final static int TYPE_HEAD = 6;
	public final static int TYPE_APK = 7;
	
	/**
	 * 鐩爣鐩綍
	 */
	private final int DIRECTORY_EXTERNAL_SDCARD = 0;
	private final int DIRECTORY_INTERNAL_SDCARD = 1;
	private final int DIRECTORY_PHONE = 2;
	
	/**
	 * 鏂囦欢鏍煎紡
	 */
	private final String FORMAT_VIDEO = ".vid";
	private final String FORMAT_IMAGE = ".jpg";
	private final String FORMAT_IMAGE_PNG = ".png";
	private final String FORMAT_IMAGE_JPEG = ".jpeg";
	private final String FORMAT_IMAGE_BMP = ".bmp";
	private final String FORMAT_TXT = ".txt";
	private final String FORMAT_LOG = ".log";
	private final String FORMAT_AUDIO = ".mp4";
	private final String FORMAT_AUDIO2 = ".m4a";
	private final String FORMAT_DATA = ".dat";
	private final String FORMAT_APK = ".apk";
	
	/**
	 * 鐩綍鍚�
	 */
	private final String ROOT_DIRECTORY_NAME = "JISOU/";
	private final String AUDIO_DIRECTORY_NAME = "audio/";
	private final String VIDEO_DIRECTORY_NAME = "video/";
	private final String TXT_DIRECTORY_NAME = "txt/";
	private final String LOG_DIRECTORY_NAME = "log/";
	private final String IMAGE_DIRECTORY_NAME = "image/";
	private final String DATA_DIRECTORY_NAME = "data/";
	private final String HEAD_DIRECTORY_NAME = "avatar/";
	private final String APK_DIRECTORY_NAME = "apk/";
	
	
	
	/**
	 * 澶栫疆瀛樺偍鍗￠璀︿复鐣屽�
	 */
	private long mExternalSDCardWarnningLimitSpace = DEF_EXTERNAL_SDCARD_WARNNING_LIMIT_SPACE_SIZE;
	
	/**
	 * 鍐呯疆瀛樺偍鍗￠璀︿复鐣屽�
	 */
	private long mInternalSDCardWarnningLimitSpace = DEF_INTERNAL_SDCARD_WARNNING_LIMIT_SPACE_SIZE;
	
	/**
	 * 鎵嬫満棰勮涓寸晫鍊�
	 */
	private long mPhoneWarnningLimitSpace = DEF_PHONE_WARNNING_LIMIT_SPACE_SIZE;
	
//	/**
//	 * 澶栫疆瀛樺偍鍗℃嫆鍐欎复鐣屽�
//	 */
//	private long mExternalSDCardUnwriteLimitSpace = DEF_EXTERNAL_SDCARD_UNWRITE_LIMIT_SPACE_SIZE;
//	
//	/**
//	 * 鍐呯疆瀛樺偍鍗℃嫆鍐欎复鐣屽�
//	 */
//	private long mInternalSDCardUnwriteLimitSpace = DEF_INTERNAL_SDCARD_UNWRITE_LIMIT_SPACE_SIZE;
//	
//	/**
//	 * 鎵嬫満鎷掑啓涓寸晫鍊�
//	 */
//	private long mPhoneUnwriteLimitSpace = DEF_PHONE_UNWRITE_LIMIT_SPACE_SIZE;
	
	/**
	 * 瑙嗛澶у皬鏈�ぇ鍊�
	 */
	private long mVideoMaxSize = DEF_VIDEO_MAX_SIZE;
	
	/**
	 * 鍥剧墖澶у皬鏈�ぇ鍊�
	 */
	private long mImageMaxSize = DEF_IMAGE_MAX_SIZE;
	
	/**
	 * 鏂囨湰澶у皬鏈�ぇ鍊�
	 */
	private long mTxtMaxSize = DEF_TXT_MAX_SIZE;
	
	/**
	 * 鏃ュ織澶у皬鏈�ぇ鍊�
	 */
	private long mLogMaxSize = DEF_LOG_MAX_SIZE;
	
	/**
	 * 闊抽澶у皬鏈�ぇ鍊�
	 */
	private long mAudioMaxSize = DEF_AUDIO_MAX_SIZE;
	
	/**
	 * 鑷畾涔夋暟鎹ぇ灏忔渶澶у�
	 */
	private long mDataMaxSize = DEF_DATA_MAX_SIZE;
	
	/**
	 * 澶村儚澶у皬鏈�ぇ鍊�
	 */
	private long mHeadMaxSize = DEF_HEAD_MAX_SIZE;
	
	/**
	 * APK澶у皬鏈�ぇ鍊�
	 */
	private long mApkMaxSize = DEF_APK_MAX_SIZE;
	
	/**
	 * 澶栫疆SD鍗¤矾寰�
	 */
	private String mExternalSDCardPath = "";
	
	/**
	 * 鍐呯疆SD鍗¤矾寰�
	 */
	private List<String> mInternalSDCardPath = new ArrayList<String>();
	
	/**
	 * 鎵嬫満鏈韩瀛樺偍绌洪棿璺緞
	 */
	private String mPhoneDataPath = "";
	
	/**
	 * 瀛樺偍绌洪棿鐩稿簲鐩綍鏄惁宸茬粡鍒涘缓
	 */
	private boolean isMakeExternalSDCardDirectory;
	private boolean isMakeInternalSDCardDirectory;
	private boolean isMakePhoneDataDirectroy;
	
	
	private static MultiCard instance;
	
	  
	/**
	 * 鑾峰緱鍗曞疄渚嬫帴鍙�
	 */
    public static MultiCard getInstance(Context context) {
        if (instance == null) {
            instance = new MultiCard();
            instance.setInternalSDCard(ReflectionUtil.getInternalSDCardPath(context));
            instance.mExternalSDCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
//            Util.cjxLog("澶栫疆SD鍗¤矾寰勶細", instance.mExternalSDCardPath);
        //    instance.mPhoneDataPath = Environment.getDataDirectory().getAbsolutePath();	//娌℃潈闄�
            instance.mPhoneDataPath = context.getFilesDir().getAbsolutePath();
//            Util.cjxLog("鎵嬫満DATA璺緞锛�, instance.mPhoneDataPath);
        }
        instance.makeAllDirectory();
        return instance;
    }
    
    public static void init(Context context) {
    	getInstance(context);
    }
    
    /**
     * 鏂囦欢鍏ㄥ悕杞粷瀵硅矾寰勶紙璇伙級
     * @param fileName 鏂囦欢鍏ㄥ悕锛堟枃浠跺悕.鎵╁睍鍚嶏級
     * @return 杩斿洖缁濆璺緞
     */
    public String getReadPath(String fileName){
    	if(TextUtils.isEmpty(fileName)) {
    		return "";
    	}
    	
    	int fileType = getFileType(fileName);
    	List<StorageDirectory> directories = getDirectorysByPriority(fileType);
    	for(StorageDirectory directory : directories) {
    		String filePath = directory.path + fileName;
    		File file = new File(filePath);
    		if(file.exists()) {
//    			Util.cjxLog("read path:", filePath);
    			return filePath;
    		}
    	}
    	return "";
    }
    
    /**
     * 鏂囦欢鍏ㄥ悕杞粷瀵硅矾寰勶紙鍐欙級
     * @param fileName 鏂囦欢鍏ㄥ悕锛堟枃浠跺悕.鎵╁睍鍚嶏級
     * @return 杩斿洖缁濆璺緞淇℃伅
     * @throws LimitSpaceUnwriteException 鍐呭瓨涓嶈冻
     * @throws IllegalArgumentException 鏂囦欢鍚嶄笉鍚堟硶
     */
    public MultiCardFilePath getWritePath(String fileName) throws LimitSpaceUnwriteException, IllegalArgumentException {
    	if(TextUtils.isEmpty(fileName)) {
    		throw new IllegalArgumentException();
    	}
    	
    	int fileType = getFileType(fileName);
    	StorageDirectory directory = limitSpaceUnwrite(fileType, 0);
    	if(directory == null) {
    		throw new LimitSpaceUnwriteException();
    	} else {
    		MultiCardFilePath multiCardFilePath = new MultiCardFilePath();
    		multiCardFilePath.setFilePath(directory.path + fileName);
    		if(limitSpaceWarnning(directory, fileType)) {
    			multiCardFilePath.setCode(MultiCardFilePath.RET_LIMIT_SPACE_WARNNING);
    		} else {
    			multiCardFilePath.setCode(MultiCardFilePath.RET_OK);
    		}
    		return multiCardFilePath;
    	}
    }
    
    /**
     * 璁剧疆鍐呯疆SD鍗＄洰褰�
     * @param directories
     */
    public void setInternalSDCard(List<String> directories) {
    	mInternalSDCardPath = directories;
    }
	
    //#0001 +{
    /**
     * 鍒犻櫎鎵�湁澶村儚
     * @param noDel 涓嶅垹闄ょ殑鏂囦欢
     */
    public void deleteAllHeaderImage(String noDel){
    	int fileType = TYPE_HEAD;
    	List<StorageDirectory> directories = getDirectorysByPriority(fileType);
    	for(StorageDirectory directory : directories) {
    		File file = new File(directory.path);
    		if (file.exists() && file.isDirectory()) {
				String[] strings = file.list();
				for (String string : strings) {
					if (string.equals(noDel)) {
						continue;
					}
					File avatarFile = new File(directory.path + string);
					if (avatarFile.exists()) {
						avatarFile.delete();
					}
				}
			}
    	}
    }
    //#0001 +}
    
    /**
     * 澶栫疆鍜屽唴缃甋D鍗℃槸鍚﹀瓨鍦�
     * @return
     */
    public boolean isSDCardExist() {
    	return isExternalSDCardExist() || isInternalSDCardExist();
    }
    
    /**
     * 鑾峰彇澶栫疆瀛樺偍鍗″墿浣欑┖闂�
     * @return
     */
    public long getExternalSDCardSpace() {
    	return getResidualSpace(mExternalSDCardPath);
    }
    
    /**
     * 鑾峰彇鍐呯疆瀛樺偍鍗″墿浣欑┖闂�
     * @return
     */
    public long getInternalSDCardSpace() {
    	if(mInternalSDCardPath != null && mInternalSDCardPath.size() > 0) {
    		long size = 0;
    		for(String path:mInternalSDCardPath) {
    			long space = getResidualSpace(path);
    			if(space > size) {
    				size = space;
    			}
    		}
    		
    		return size;
    	} else {
    		return 0;
    	}
    }
    
    /**
     * 鏍规嵁鏂囦欢绫诲瀷锛屾鏌ユ槸鍚︽湁鍙啓绌洪棿
     * fileType 鏂囦欢绫诲瀷
     * type 妫�煡鐨勮寖鍥�0琛ㄧず鎵�湁,1琛ㄧず鍙鏌ュ缃�
     * @return
     */
    public boolean checkSDCardSpace(int fileType, int type) {
    	StorageDirectory directory = limitSpaceUnwrite(fileType, type);
    	if(directory != null) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    private MultiCard() {
		
	}

    /**
     * 鏄惁鍥犵┖闂翠笉瓒虫姤璀�
     * @param directory 瀛樺偍鐩綍
     * @param fileType 璇锋眰鍐欐搷浣滅殑鏂囦欢绫诲瀷
     * @return 杩斿洖true琛ㄧず鍐呭瓨棰勮锛屽惁鍒欏弽涔�
     */
    private boolean limitSpaceWarnning(StorageDirectory directory, int fileType) {
    	if(getDirectoryWarnningLimitSpace(directory.type) > directory.residualSpace) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /**
     * 鏄惁鍥犵┖闂翠笉瓒虫嫆鍐�
     * @param fileType 璇锋眰鍐欐搷浣滅殑鏂囦欢绫诲瀷
     * @return 杩斿洖婊¤冻鍐欐潯浠剁殑鏈�紭鐩綍锛涘鏋滆繑鍥瀗ull琛ㄧず娌℃湁鐩綍婊¤冻鏉′欢
     */
    private StorageDirectory limitSpaceUnwrite(int fileType, int type) {
    	List<StorageDirectory> directories = getDirectorysByPriority(fileType);
    	for(StorageDirectory directory : directories) {
    		//濡傛灉鍙鏌ュ缃瓨鍌ㄥ崱鍒欒繃婊や笅
    		if(type == 1 && directory.type != DIRECTORY_EXTERNAL_SDCARD) {
    			continue;
    		}
    		long residualSpace = getResidualSpace(directory.rootDirectory);
    		if(getFileMaxSize(fileType) <= residualSpace) {
    			directory.residualSpace = residualSpace;
        		return directory;
        	}
    	}
    	return null;
    }
    
    /**
     * 鏍规嵁鏂囦欢绫诲瀷鍙婃鏌ヨ寖鍥达紝鍒ゆ柇鏄惁闇�绌洪棿棰勮
     * @param fileType 鏂囦欢绫诲瀷
     * @param type 妫�煡鑼冨洿,0琛ㄧず鍏ㄩ儴,1琛ㄧず澶栫疆
     * @return 杩斿洖缁濆璺緞淇℃伅
     */
    public boolean islimitSpaceWarning(int fileType, int type) {
    	StorageDirectory directory = limitSpaceUnwrite(fileType, type);
    	if(directory == null) {
    		return false;
    	} else {
    		return !limitSpaceWarnning(directory, fileType);
    	}
    }
    
    /**
     * 鑾峰彇鐩綍鍓╀綑绌洪棿
     * @param directoryPath
     * @return
     */
    private long getResidualSpace(String directoryPath) {
    	try {
    		StatFs sf = new StatFs(directoryPath);
        	long blockSize = sf.getBlockSize();
        	long availCount = sf.getAvailableBlocks();
        	long availCountByte = availCount * blockSize;
        	return availCountByte;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return 0;
    }
    
    /**
     * 鑾峰彇鏂囦欢鏈�ぇ澶у皬
     * @param type
     * @return
     */
    private long getFileMaxSize(int type) {
    	switch (type) {
		case TYPE_AUDIO:
			return mAudioMaxSize;
			
		case TYPE_VIDEO:
			return mVideoMaxSize;
			
		case TYPE_TXT:
			return mTxtMaxSize;
			
		case TYPE_LOG:
			return mLogMaxSize;
			
		case TYPE_IMAGE:
			return mImageMaxSize;
		
		case TYPE_DATA:
			return mDataMaxSize;
			
		case TYPE_HEAD:
			return mHeadMaxSize;
			
		case TYPE_APK:
			return mApkMaxSize;

		default:
			return mDataMaxSize;
		}
    }
    
    /**
     * 鎸変紭鍏堢骇鍙栧嚭鎵�湁鐩綍
     * @param fileType
     * @return
     */
    private List<StorageDirectory> getDirectorysByPriority(int fileType) {
    	String endDirectoryName = String.format("/%s%s", ROOT_DIRECTORY_NAME, getDirectoryName(fileType));
    	List<StorageDirectory> directories = new ArrayList<StorageDirectory>();
    	if(fileType == TYPE_HEAD) {
    		directories.add(new StorageDirectory(mPhoneDataPath + endDirectoryName, DIRECTORY_PHONE, mPhoneDataPath));
    		if(isInternalSDCardExist()) {
    			for(String directory : mInternalSDCardPath) {
    				directories.add(new StorageDirectory(directory + endDirectoryName, DIRECTORY_INTERNAL_SDCARD, directory));
    			}
    		}
    		if(isExternalSDCardExist()) {
    			directories.add(new StorageDirectory(mExternalSDCardPath + endDirectoryName, DIRECTORY_EXTERNAL_SDCARD, mExternalSDCardPath));
    		}
    	} else {
    		if(isExternalSDCardExist()) {
    			directories.add(new StorageDirectory(mExternalSDCardPath + endDirectoryName, DIRECTORY_EXTERNAL_SDCARD, mExternalSDCardPath));
    		}
    		if(isInternalSDCardExist()) {
    			for(String directory : mInternalSDCardPath) {
    				directories.add(new StorageDirectory(directory + endDirectoryName, DIRECTORY_INTERNAL_SDCARD, directory));
    			}
    		}
    		
    		//鍥剧墖,闊抽锛岃棰戦渶瑕佸閮ㄧ▼搴忎娇鐢�澶栭儴绋嬪簭鏃犳硶璁块棶娌欑洅锛屼笉鏀寔
    		if(fileType == TYPE_DATA || fileType == TYPE_TXT || fileType == TYPE_LOG || fileType == TYPE_HEAD || fileType == TYPE_APK) {
    			directories.add(new StorageDirectory(mPhoneDataPath + endDirectoryName, DIRECTORY_PHONE, mPhoneDataPath));
    		}
    	}
    	return directories;
    }

    /**
     * 鑾峰彇鐩綍鎶ヨ涓寸晫绌洪棿
     * @param directory
     * @return
     */
    private long getDirectoryWarnningLimitSpace(int directory) {
    	switch (directory) {
		case DIRECTORY_EXTERNAL_SDCARD:
			return mExternalSDCardWarnningLimitSpace;

		case DIRECTORY_INTERNAL_SDCARD:
			return mInternalSDCardWarnningLimitSpace;

		case DIRECTORY_PHONE:
			return mPhoneWarnningLimitSpace;
		
		default:
			return mPhoneWarnningLimitSpace;
		}
    }
    
    /**
     * 鑾峰彇鐩綍鎷掑啓涓寸晫绌洪棿
     * @param directory
     * @return
     */
    private long getDirectoryUnwriteLimitSpace(int directory) {
    	switch (directory) {
		case DIRECTORY_EXTERNAL_SDCARD:
			return 0;

		case DIRECTORY_INTERNAL_SDCARD:
			return 0;

		case DIRECTORY_PHONE:
			return 0;
		
		default:
			return 0;
		}
    }
    
    /**
     * 鑾峰彇鏂囦欢濯掍綋绫诲瀷
     * @param fileName
     * @return
     */
    private int getFileType(String fileName) {
    	if(fileName.endsWith(FORMAT_AUDIO)) {
    		return TYPE_AUDIO;
    	} else if(fileName.endsWith(FORMAT_AUDIO2)) {
    		return TYPE_AUDIO;
    	} else if(fileName.endsWith(FORMAT_IMAGE)) {
    		return TYPE_IMAGE;
    	} else if(fileName.endsWith(FORMAT_IMAGE_PNG)) {
    	    return TYPE_IMAGE;
    	} else if(fileName.endsWith(FORMAT_IMAGE_JPEG)) {
    	    return TYPE_IMAGE;
    	} else if(fileName.endsWith(FORMAT_IMAGE_BMP)) {
    	    return TYPE_IMAGE;
    	} else if(fileName.endsWith(FORMAT_LOG)) {
    		return TYPE_LOG;
    	} else if(fileName.endsWith(FORMAT_TXT)) {
    		return TYPE_TXT;
    	} else if(fileName.endsWith(FORMAT_VIDEO)) {
    		return TYPE_VIDEO;
    	} else if(fileName.endsWith(FORMAT_DATA)){
    		return TYPE_DATA;
    	} else if(fileName.endsWith(FORMAT_APK)) {
    		return TYPE_APK;
    	} else {
    		return TYPE_HEAD;
    	}
    }
    
    /**
     * 鑾峰彇鏂囦欢瀵瑰簲鐨勭洰褰曞悕
     * @param fileType
     * @return
     */
    private String getDirectoryName(int fileType) {
    	switch (fileType) {
		case TYPE_AUDIO:
			return AUDIO_DIRECTORY_NAME;
			
		case TYPE_VIDEO:
			return VIDEO_DIRECTORY_NAME;
			
		case TYPE_TXT:
			return TXT_DIRECTORY_NAME;
			
		case TYPE_LOG:
			return LOG_DIRECTORY_NAME;
			
		case TYPE_IMAGE:
			return IMAGE_DIRECTORY_NAME;
		
		case TYPE_DATA:
			return DATA_DIRECTORY_NAME;
			
		case TYPE_HEAD:
			return HEAD_DIRECTORY_NAME;
			
		case TYPE_APK:
			return APK_DIRECTORY_NAME;

		default:
			return DATA_DIRECTORY_NAME;
		}
    }
    
    /**
     * 鍒涘缓瀛樺偍绌洪棿涓嬬殑鎵�湁鐩綍
     */
    private void makeAllDirectory() {
    	makeDirectory(DIRECTORY_EXTERNAL_SDCARD);
    	makeDirectory(DIRECTORY_INTERNAL_SDCARD);
    	makeDirectory(DIRECTORY_PHONE);
    }
    
    /**
     * 鍒涘缓蹇呰鐨勭洰褰�
     * @param directoryType
     */
    private void makeDirectory(int directoryType) {
    	List<String> rootDirectorys = new ArrayList<String>();
    	switch (directoryType) {
		case DIRECTORY_EXTERNAL_SDCARD:
			rootDirectorys.add(mExternalSDCardPath);
			if(isMakeExternalSDCardDirectory || !isExternalSDCardExist()) {
				return;
			}
			isMakeExternalSDCardDirectory = true;
			break;
			
		case DIRECTORY_INTERNAL_SDCARD:
			for(String path : mInternalSDCardPath) {
				rootDirectorys.add(path);
			}
			if(isMakeInternalSDCardDirectory || !isInternalSDCardExist()) {
				return;
			}
			isMakeInternalSDCardDirectory = true;
			break;
			
		case DIRECTORY_PHONE:
			rootDirectorys.add(mPhoneDataPath);
			if(isMakePhoneDataDirectroy) {
				return;
			}
			isMakePhoneDataDirectroy = true;
			break;

		default:
			return;
		}
    	
    	for(String rootDirectory : rootDirectorys) {
    		String appDirectory = rootDirectory + "/" + ROOT_DIRECTORY_NAME;
    		makeDirectoryCheck(directoryType, mkdir(appDirectory));
//    		makeDirectoryCheck(directoryType, mkdir(appDirectory + AUDIO_DIRECTORY_NAME));
//    		makeDirectoryCheck(directoryType, mkdir(appDirectory + VIDEO_DIRECTORY_NAME));
//    		makeDirectoryCheck(directoryType, mkdir(appDirectory + TXT_DIRECTORY_NAME));
    		makeDirectoryCheck(directoryType, mkdir(appDirectory + LOG_DIRECTORY_NAME));
//    		makeDirectoryCheck(directoryType, mkdir(appDirectory + IMAGE_DIRECTORY_NAME));
//    		makeDirectoryCheck(directoryType, mkdir(appDirectory + DATA_DIRECTORY_NAME));
//    		makeDirectoryCheck(directoryType, mkdir(appDirectory + HEAD_DIRECTORY_NAME));
//    		makeDirectoryCheck(directoryType, mkdir(appDirectory + APK_DIRECTORY_NAME));
    	}
    }
    
    /**
     * 鍒涘缓鐩綍妫�煡
     * @param directoryType
     * @param mk
     */
    private void makeDirectoryCheck(int directoryType, boolean mk) {
    	if(!mk) {
    		switch (directoryType) {
    		case DIRECTORY_EXTERNAL_SDCARD:
    			isMakeExternalSDCardDirectory = false;
    			break;
    			
    		case DIRECTORY_INTERNAL_SDCARD:
    			isMakeInternalSDCardDirectory = false;
    			break;
    			
    		case DIRECTORY_PHONE:
    			isMakePhoneDataDirectroy = false;
    			break;
    		}
    	}
    }
    
    /**
     * 鍒涘缓鐩綍
     * @param path
     * @return
     */
    private boolean mkdir(String path) {
    	File file = new File(path);
		if(!file.exists()) {
			boolean mk = file.mkdir();
			if(mk) {
//				Util.cjxLog(path + "鍒涘缓鎴愬姛");
			} else {
//				Util.cjxLog(path + "鍒涘缓澶辫触");
			}
			return mk;
		}
//		Util.cjxLog(path + "宸茬粡瀛樺湪");
		return true;
    }
    
    /**
     * 澶栫疆瀛樺偍鍗℃槸鍚﹀瓨鍦�
     * @return
     */
    public boolean isExternalSDCardExist() {
    	boolean bExist = false;
    	bExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    	if(bExist && getExternalSDCardSpace() > 0) {	//瀛樺湪,骞朵笖绌洪棿澶т簬0
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /**
     * 鍐呯疆瀛樺偍鍗℃槸鍚﹀瓨鍦�
     * @return
     */
    private boolean isInternalSDCardExist() {
    	boolean bExist = false;
    	bExist = mInternalSDCardPath != null && mInternalSDCardPath.size() > 0;
    	if(bExist && getInternalSDCardSpace() > 0) {	//瀛樺湪,骞朵笖绌洪棿澶т簬0
    		return true;
    	} else {
    		return false;
    	}
    }
    
    
    /**
     * 瀛樺偍鐩綍
     * @author geolo
     *
     */
    private class StorageDirectory {
    	public String path;
    	public int type;
    	public long residualSpace;
    	public String rootDirectory;
    	
    	public StorageDirectory(String path, int type) {
    		this.path = path;
    		this.type = type;
    	}
    	
    	public StorageDirectory(String path, int type, String rootDirectory) {
    		this.path = path;
    		this.type = type;
    		this.rootDirectory = rootDirectory;
    	}
    }
}
