package com.lifang.imgsoa.facade;

import com.lifang.imgsoa.model.ImageKeyObject;

public interface ImageService {
    ImageKeyObject uploadSingleFile(byte[] data, int type);
    ImageKeyObject uploadSingleFileNoHandle2PubBucket(byte[] data);
    ImageKeyObject getImageKeyObject(String key);
    ImageKeyObject getImageKeyUrl(String key);
}
