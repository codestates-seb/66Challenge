// 현재 임의의 주소를 가져와 인증서 발급 클릭시
// 모달컴포넌트가 뜨게 만들어진 상황입니다

import { SlArrowRight } from 'react-icons/sl';
import axios from 'axios';
import { useEffect, useState } from 'react';

export const MyPageMenuList = (props) => {
  const [modal, setModal] = useState(false);
  const [apiData, SetApiData] = useState([]);

  useEffect(() => {
    axios
      .get('http://13.209.179.193:8080/habits/search?page=1&size=999')
      .then((res) => {
        SetApiData(res.data);
        console.log(apiData[0].title);
        console.log('위에 문제 없이 데이터 뜨면 잘한겁니다');
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  // MyPageMenuList 컴포넌트가 처음 렌더링되는 시점에서 한번 통신이 되기에 여기서는 useeffect가 불필요함

  const GetDataModal = () => {
    const DataTitle = apiData.map((data) => {
      return (
        <div
          className="w-80 cursor-pointer"
          onClick={() => {
            setModal(!modal);
          }}
        >
          <div className="indent-3 text-sm p-2  w-full only:flex place-content-between border-x-2 border-t-2 border-black dark:text-white dark:border-white">
            {data.title}
            <div className="pr-5">
              <button className="border-2">발급</button>
            </div>
          </div>
        </div>
      );
    });
    return <div>{DataTitle}</div>;
  };

  const openModal = () => {
    setModal(true);
  };
  const closeModal = () => {
    setModal(!modal);
  };

  return (
    <div>
      <div className="w-80 cursor-pointer" onClick={props.onClick}>
        <div className="p-2 font-bold  w-full only:flex place-content-between border-x-2 border-t-2 border-black dark:text-white dark:border-white">
          찜한 습관
          <div className="pr-5">
            <SlArrowRight className="inline align-middle dark:bg-white" />
          </div>
        </div>
      </div>
      <div className="w-80 cursor-pointer" onClick={props.onClick}>
        <div className="p-2 font-bold  w-full only:flex place-content-between border-x-2 border-t-2 border-black dark:text-white dark:border-white">
          내가 만든 습관
          <div className="pr-5">
            <SlArrowRight className="inline align-middle dark:bg-white" />
          </div>
        </div>
      </div>
      <div className="w-80 cursor-pointer" onClick={openModal}>
        <div className="p-2 font-bold  w-full only:flex place-content-between border-x-2 border-t-2 border-black dark:text-white dark:border-white">
          인증서 발급
          <div className="pr-5">
            <SlArrowRight className="inline align-middle dark:bg-white" />
          </div>
        </div>
      </div>
      {modal && <GetDataModal />}
      {/* 똑같은 코드 = {modal ===  true ? <GetDataModal /> : null} */}
      <div className="w-80 cursor-pointer hidden" onClick={props.onClick}>
        <div className="p-2 font-bold  w-full only:flex place-content-between border-x-2 border-t-2 border-black dark:text-white dark:border-white">
          숨겨진 업적
          <div className="pr-5">
            <SlArrowRight className="inline align-middle dark:bg-white" />
          </div>
        </div>
      </div>
      <div className="w-80 cursor-pointer" onClick={props.onClick}>
        <div className="p-2 font-bold  w-full only:flex place-content-between border-x-2 border-t-2 border-black dark:text-white dark:border-white">
          친구 초대
          <div className="pr-5">
            <SlArrowRight className="inline align-middle dark:bg-white" />
          </div>
        </div>
      </div>
      <div className="w-80 cursor-pointer" onClick={props.onClick}>
        <div className="p-2 font-bold  w-full only:flex place-content-between border-2 border-black dark:text-white dark:border-white">
          고객센터
          <div className="pr-5">
            <SlArrowRight className="inline align-middle dark:bg-white" />
          </div>
        </div>
      </div>

      <div className="my-3">
        <div className="w-80 cursor-pointer" onClick={props.onClick}>
          <div className="p-2 font-bold  w-full only:flex place-content-between border-x-2 border-t-2 border-black dark:text-white dark:border-white">
            회원 정보 수정
            <div className="pr-5">
              <SlArrowRight className="inline align-middle dark:bg-white" />
            </div>
          </div>
        </div>
        <div className="w-80 cursor-pointer" onClick={props.onClick}>
          <div className="p-2 font-bold  w-full only:flex place-content-between border-x-2 border-t-2 border-black dark:text-white dark:border-white">
            로그아웃
            <div className="pr-5">
              <SlArrowRight className="inline align-middle dark:bg-white" />
            </div>
          </div>
        </div>
        <div className="w-80 cursor-pointer" onClick={props.onClick}>
          <div className="p-2 font-bold  w-full only:flex place-content-between border-2 border-black dark:text-white dark:border-white">
            회원탈퇴
            <div className="pr-5">
              <SlArrowRight className="inline align-middle dark:bg-white" />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
