/* 
* <------  사용하시기 전에 꼭 읽어주세요! ------> *
ToDo 1. 사용하시는 컴포넌트에서 props로 imgFile setImgFile, uploadController 넘겨주셔야 합니다.

* <------  사용하시기 전에 꼭 읽어주세요! ------> *
*/

import { useRef } from 'react';
import { MdOutlineDriveFolderUpload } from 'react-icons/md';

export const FileUploader = ({ imgFile, setImgFile, uploadController }) => {
  const imgRef = useRef();

  const saveImgFile = () => {
    const file = imgRef.current.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onloadend = () => {
      setImgFile(reader.result);
      uploadController();
    };
  };
  return (
    <div className="file-uploader-container">
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
      />
    </div>
  );
};
