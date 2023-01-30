import { BsGithub } from 'react-icons/bs';
import { TbBrandNextjs } from 'react-icons/tb';
import {
  SiTailwindcss,
  SiSpringboot,
  SiTypescript,
  SiNotion,
  SiSpringsecurity,
  SiNginx,
  SiGithubactions,
  SiUbuntu,
  SiRedux,
  SiPrettier,
  SiStyledcomponents,
} from 'react-icons/si';
import { IoLogoPwa } from 'react-icons/io5';
import { GrMysql } from 'react-icons/gr';
import { FaAws, FaJenkins } from 'react-icons/fa';
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
      <div className="bg-footerColor text-center text-sm mt-5 ">
        <h4 className="text-gray-pink text-base font-bold pt-10 pb-5 dark:text-white">
          66 Challenge
        </h4>
        <div className="flex justify-center gap-10 px-5 border-b-2 pb-5">
          <div className="mx-2 w-36">
            <h4 className="font-semibold mb-2 text-gray-pink">FE</h4>
            <div className="font-medium text-gray-pink flex flex-col gap-1">
              {FeMemberList}
            </div>
          </div>
          <div className="mx-2 w-36">
            <h4 className="font-semibold mb-2 text-gray-pink">BE</h4>
            <div className="font-medium text-gray-pink flex flex-col gap-1">
              {BeMemberList}
            </div>
          </div>
        </div>
        <div className="text-gray-pink px-2 pt-5">
          <p className="text-gray-pink my-1">Made by these (FE)</p>
          <ul className="flex justify-center my-3">
            <li className="mx-2">
              <SiTypescript className="w-6 h-6" />
            </li>
            <li className="mx-2">
              <TbBrandNextjs className="w-6 h-6" />
            </li>
            <li className="mx-2">
              <SiRedux className="w-6 h-6" />
            </li>
            <li className="mx-2">
              <SiTailwindcss className="w-6 h-6" />
            </li>
            <li className="mx-2">
              <SiStyledcomponents className="w-6 h-6" />
            </li>
            <li className="mx-2">
              <IoLogoPwa className="w-6 h-6" />
            </li>
            <li className="mx-2">
              <FaJenkins className="w-6 h-6" />
            </li>
            <li className="mx-2">
              <SiPrettier className="w-6 h-6" />
            </li>
          </ul>
        </div>
        <div className="text-gray-pink px-2 pb-5 border-b-2">
          <p className="text-gray-pink my-1">Made by these (BE)</p>
          <ul className="flex justify-center my-3">
            <li className="mx-2">
              <GrMysql className="w-6 h-6" />
            </li>
            <li className="mx-2">
              <SiSpringboot className="w-6 h-6" />
            </li>
            <li className="mx-2">
              <FaAws className="w-6 h-6" />
            </li>
            <li className="mx-2">
              <SiSpringsecurity className="w-6 h-6" />
            </li>
            <li className="mx-2">
              <SiNginx className="w-6 h-6" />
            </li>
            <li className="mx-2">
              <SiGithubactions className="w-6 h-6" />
            </li>
            <li className="mx-2">
              <SiUbuntu className="w-6 h-6" />
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
