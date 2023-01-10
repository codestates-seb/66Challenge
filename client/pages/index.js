import { SlideBanner } from '../components/slideBanner';
import { HabitWrapperHorizontal } from '../components/habitWrapperHorizontal';

export default function Home() {
  // const testData = [
  //   {
  //     bgImgUrl: '',
  //     contText: 'test용 첫 번째 슬라이드입니다.',
  //     contSubText: '여기는 부제가 들어가겠죠',
  //     bannerLink: '',
  //   },
  //   {
  //     bgImgUrl: '',
  //     contText: 'test용 두 번째 슬라이드입니다.',
  //     contSubText: '여기는 부제가 들어가겠죠',
  //     bannerLink: '',
  //   },
  //   {
  //     bgImgUrl: '',
  //     contText: 'test용 세 번째 슬라이드입니다.',
  //     contSubText: '여기는 부제가 들어가겠죠',
  //     bannerLink: '',
  //   },
  // ];

  const testData = [
    {
      habitImage: '/image/running.png',
      habitTitle: '달리기 3km',
      habitBody:
        '매일 아침에 3km 달리기를 실천합니다. 상쾌한 아침을 맞이하며 건강도 챙겨보세요!',
    },
    {
      habitImage: '/image/running.png',
      habitTitle: '달리기 3km',
      habitBody:
        '매일 아침에 3km 달리기를 실천합니다. 상쾌한 아침을 맞이하며 건강도 챙겨보세요!',
    },
    {
      habitImage: '/image/running.png',
      habitTitle: '달리기 3km',
      habitBody:
        '매일 아침에 3km 달리기를 실천합니다. 상쾌한 아침을 맞이하며 건강도 챙겨보세요!',
    },
    {
      habitImage: '/image/running.png',
      habitTitle: '달리기 3km',
      habitBody:
        '매일 아침에 3km 달리기를 실천합니다. 상쾌한 아침을 맞이하며 건강도 챙겨보세요!',
    },
  ];

  return (
    <div>
      <HabitWrapperHorizontal
        habitWrapperTitle="실시간 인기 습관"
        habitWrapperData={testData}
      />
    </div>
  );
}
