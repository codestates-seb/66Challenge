/* 
* <------  사용하시기 전에 꼭 읽어주세요! ------> *

* <------  사용하시기 전에 꼭 읽어주세요! ------> *
*/

import { useState, useRef } from 'react';
import { MdOutlineDriveFolderUpload } from 'react-icons/md';

export const FileUploader = () => {
  const [imgFile, setImgFile] = useState('');
  const imgRef = useRef();

  const saveImgFile = () => {
    const file = imgRef.current.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onloadend = () => {
      setImgFile(reader.result);
    };
  };
  return (
    <form className="file-uploader-container">
      <label
        className="file-uploader-label flex justify-center items-center w-[302px] mx-auto h-[202px] border rounded"
        htmlFor="uploader-input"
      >
        {imgFile ? (
          <div className="file-uploader-preview w-[400px] h-[200px]">
            <img
              className="object-contain w-full h-full"
              src={imgFile}
              alt="uploaded image"
            />
          </div>
        ) : (
          <div className="file-uploader-base-image">
            <MdOutlineDriveFolderUpload size="40" />
          </div>
        )}
      </label>
      <input
        className="file-uploader-input hidden"
        id="uploader-input"
        type="file"
        accept="image/*"
        ref={imgRef}
        onChange={saveImgFile}
        capture="gallery"
      />
    </form>
  );
};
