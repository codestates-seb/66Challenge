/* 
* <------  사용하시기 전에 꼭 읽어주세요! ------> *
ToDo 1. 사용하시는 컴포넌트에서 props로 미리보기를 위한 state를 선언한 후 imgFilePreview(state)와 register를 넘겨주셔야 합니다.
ToDo 2. 사용하시는 컴포넌트에서 React-Hook-Form의 watch 메서드와 state, 그리고 URL.createObjectURL 메서드를 사용하여야 합니다.
          const { habitImage } = watch();
          const [habitImagePreview, setHabitImagePreview] = useState('');

          useEffect(() => {
            if (habitImage && habitImage.length > 0) {
              const file = habitImage[0];
              setHabitImagePreview(URL.createObjectURL(file));
            }
          }, [habitImage]);

ToDo 3. 사용하시는 컴포넌트에서 onSubmit에 대한 Handler에 아래와 같은 코드를 추가하여 formData를 서버에 전송할 수 있습니다.
        const formData = new FormData();
        formData.append('file', data.file[0]);

* <------  사용하시기 전에 꼭 읽어주세요! ------> *
*/

import React from 'react';
import { MdOutlineDriveFolderUpload } from 'react-icons/md';
import Image from 'next/image';
import { UseFormRegisterReturn } from 'react-hook-form';

interface FileUploaderProps {
  imgFilePreview: string;
  register: UseFormRegisterReturn;
  disabled?: boolean;
}

export const FileUploader = ({
  imgFilePreview,
  register,
  disabled,
}: FileUploaderProps) => {
  const { name } = register;

  const ifPreviewClassName: string = `border rounded`;

  return (
    <div className="file-uploader-container">
      <label
        className={`${
          imgFilePreview ? null : ifPreviewClassName
        } file-uploader-label flex justify-center items-center w-[302px] mx-auto h-[202px]`}
        htmlFor={`uploader-input-${name}`}
      >
        {imgFilePreview ? (
          <div className="file-uploader-preview">
            <Image
              className="object-contain w-[202px] h-[202px]"
              src={imgFilePreview}
              alt="uploaded image"
              width={1000}
              height={1000}
              style={{ objectFit: 'cover' }}
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
        id={`uploader-input-${name}`}
        type="file"
        accept=".jpeg, .png, .jpg"
        {...register}
        disabled={disabled}
      />
    </div>
  );
};
