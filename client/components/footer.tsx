import { BsGithub } from 'react-icons/bs';
import { SiNotion } from 'react-icons/si';
import { SiTypescript } from 'react-icons/si';
import { TbBrandNextjs } from 'react-icons/tb';
import { SiTailwindcss } from 'react-icons/si';
import { IoLogoPwa } from 'react-icons/io5';
import { GrMysql } from 'react-icons/gr';

interface memberDataType {
  id: number;
  name: string;
  gitUrl: string;
}

export const Footer = () => {
  const feMemberData: Array<memberDataType> = [
    { id: 1, name: '박정우(팀장)', gitUrl: 'https://github.com/jwParkDev' },
    { id: 2, name: '김동규', gitUrl: 'https://github.com/rbaam' },
    { id: 3, name: '배성진', gitUrl: 'https://github.com/Menat91' },
    { id: 4, name: '하수환', gitUrl: 'https://github.com/hasuhwan' },
  ];

  const beMemberData: Array<memberDataType> = [
    { id: 1, name: '강은영', gitUrl: 'https://github.com/greenkey20' },
    { id: 2, name: '김찬빈', gitUrl: 'https://github.com/dev-jambin' },
    { id: 3, name: '선우예림', gitUrl: 'https://github.com/yerimsw' },
  ];

  const FeMemberList = feMemberData.map((member) => {
    return (
      <div id={String(member.id)} key={member.id}>
        <div className="flex justify-between">
          <span className="text-gray-pink"> {member.name}</span>
          <a href={member.gitUrl} target="_blank" rel="noreferrer">
            <BsGithub className="h-5 w-5" />
          </a>
        </div>
      </div>
    );
  });

  const BeMemberList = beMemberData.map((member) => {
    return (
      <div id={String(member.id)} key={member.id}>
        <div className="flex justify-between">
          <span className="text-gray-pink"> {member.name}</span>

          <a href={member.gitUrl} target="_blank" rel="noreferrer">
            <BsGithub className="h-5 w-5" />
          </a>
        </div>
      </div>
    );
  });

  return (
    <>
      <div className="bg-footerColor text-center text-sm mt-5">
        <h4 className="text-gray-pink text-base font-bold pt-10 pb-5 dark:text-white">
          66 Challenge
        </h4>
        <div className="flex justify-center gap-10">
          <div className="mx-2 w-28">
            <h4 className="font-semibold mb-2 text-gray-pink">FE</h4>
            <div className="font-medium text-gray-pink flex flex-col gap-1">
              {FeMemberList}
            </div>
          </div>
          <div className="mx-2 w-28">
            <h4 className="font-semibold mb-2 text-gray-pink">BE</h4>
            <div className="font-medium text-gray-pink flex flex-col gap-1">
              {BeMemberList}
            </div>
          </div>
        </div>
        <div className="text-gray-pink py-5 border-b-2">
          제작기간: 2023.01.03 ~ 2023.02.03
        </div>

        <div className="text-gray-pink px-2 py-5 border-b-2">
          <p className="text-gray-pink my-1">Made by these</p>
          <ul className="flex justify-center my-3">
            <li className="mx-2">
              <SiTypescript className="w-6 h-6" />
            </li>
            <li className="mx-2">
              <TbBrandNextjs className="w-6 h-6" />
            </li>
            <li className="mx-2">
              <SiTailwindcss className="w-6 h-6" />
            </li>
            <li className="mx-2">
              <IoLogoPwa className="w-6 h-6" />
            </li>
            <li className="mx-2">
              <GrMysql className="w-6 h-6" />
            </li>
          </ul>
        </div>

        <div className="flex justify-center pt-5 pb-10">
          <a href="https://github.com/codestates-seb/seb41_main_028">
            <BsGithub className="cursor-pointer mx-2 w-6 h-7 text-gray-pink" />
          </a>
          <a href="https://www.notion.so/codestates/5aaf9053ee9c41bfb866e9ac4f4763ea">
            <SiNotion className="cursor-pointer mx-2 w-6 h-7 text-gray-pink" />
          </a>
        </div>
      </div>
    </>
  );
};
