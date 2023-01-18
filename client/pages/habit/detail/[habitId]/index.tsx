import { useRouter } from 'next/router';
import Image from 'next/image';
import { DropDown } from '../../../../components/dropDown';
import { AiFillStar } from 'react-icons/ai';

const HabitDetail: React.FC = () => {
  const router = useRouter();
  const habitId = router.query.habitId;

  return (
    <div className="habit-detail-container">
      <div className="habit-detail-top">
        <div className="habit-detail-top-image">
          <Image
            src="/image/running.png"
            alt="habit image"
            width={500}
            height={500}
          />
        </div>
        <div className="habit-detail-top-info p-5 border-b border-borderColor">
          <div className="habit-detail-title-container flex justify-between items-center mb-2 pt-5">
            <h2 className="habit-detail-title text-2xl font-bold">{`습관 제목`}</h2>
            <DropDown />
          </div>
          <div className="habit-detail-metainfo-container flex items-center gap-2.5">
            <div className="habit-detail-postuser">{`게시한 유저 이름`}</div>
            <div className="habit-detail-score-container flex items-center gap-1">
              <AiFillStar className="text-subColor" />
              <span className="text-sm ">{`score`}</span>
            </div>
          </div>
        </div>
      </div>
      <div className="habit-detail-middle p-5 border-b border-borderColor">
        <div className="habit-detail-body">
          <h3 className="text-lg font-semibold pb-5">상세내용</h3>
          <p className="pb-2.5">다 같이 매일 아침 3km 달리기를 시작해봅시다!</p>
          <p className="pb-2.5">
            유산소 운동의 가장 기본이 되는 달리기는 체력 증진 뿐만 아니라 체중
            감소 및 혈액순환에 도움이 됩니다.
          </p>
          <p className="pb-2.5">
            더불어 매일 아침에 달리는 습관을 만듦으로써 하루를 일찍 시작하여
            아침형 인간이 될 수 있을 뿐만 아니라 남들보다 매일 매일을 보다 길게
            보낼 수 있습니다.
          </p>
          <p className="pb-2.5">언제까지 미루실건가요? 지금 바로 시작하시죠!</p>
        </div>
      </div>
      <div className="habit-detail-bottom p-5">
        <h3 className="text-lg font-semibold pb-5">인증방법</h3>
        <p className="pb-2.5">
          인증 가능한 시간대는 매일&nbsp;
          <span className="text-subColor font-bold">{`00:00`}</span>부터{' '}
          <span className="text-subColor font-bold">{`24:00`}</span>까지 입니다.
        </p>
        <p>인증 사진의 올바른 예와 잘못된 예는 아래와 같습니다.</p>
        <div className="pt-5 flex gap-5">
          <div className="flex flex-col">
            <Image
              src={`/image/running.png`}
              alt="correct auth image"
              width={500}
              height={500}
            />
            <div className="text-center text-green-600 pt-2.5 text-sm font-bold">
              {'올바른 인증사진'}
            </div>
          </div>
          <div className="flex flex-col">
            <Image
              src={`/image/running.png`}
              alt="incorrect auth image"
              width={500}
              height={500}
            />
            <div className="text-center text-rose-600 pt-2.5 text-sm font-bold">
              {'잘못된 인증사진'}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HabitDetail;
