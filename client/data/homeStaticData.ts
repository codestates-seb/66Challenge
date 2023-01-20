interface slideDataType {
  bgImgUrl: string;
  contText: string;
  contSubText: string;
  bannerLink: string;
}

export const slideData: Array<slideDataType> = [
  {
    bgImgUrl: '',
    contText: 'test용 첫 번째 슬라이드입니다.',
    contSubText: '여기는 부제가 들어가겠죠',
    bannerLink: '',
  },
  {
    bgImgUrl: '',
    contText: 'test용 두 번째 슬라이드입니다.',
    contSubText: '여기는 부제가 들어가겠죠',
    bannerLink: '',
  },
  {
    bgImgUrl: '',
    contText: 'test용 세 번째 슬라이드입니다.',
    contSubText: '여기는 부제가 들어가겠죠',
    bannerLink: '',
  },
];

interface categoryDataType {
  cateImgUrl: string;
  cateTitle: string;
  categoryId: string;
}

export const categoryData: Array<categoryDataType> = [
  {
    cateImgUrl: '/image/icons/exercise.png',
    cateTitle: '운동',
    categoryId: '1',
  },
  {
    cateImgUrl: '/image/icons/food.png',
    cateTitle: '식습관',
    categoryId: '2',
  },
  {
    cateImgUrl: '/image/icons/study.png',
    cateTitle: '학습',
    categoryId: '3',
  },
  {
    cateImgUrl: '/image/icons/life.png',
    cateTitle: '일상생활',
    categoryId: '4',
  },
  {
    cateImgUrl: '/image/icons/hobby.png',
    cateTitle: '취미',
    categoryId: '5',
  },
  {
    cateImgUrl: '/image/icons/selfcare.png',
    cateTitle: '자기관리',
    categoryId: '6',
  },
  {
    cateImgUrl: '/image/icons/eco.png',
    cateTitle: '환경',
    categoryId: '7',
  },
  {
    cateImgUrl: '/image/icons/etc.png',
    cateTitle: '기타',
    categoryId: '8',
  },
];

interface habitWrapperDataType {
  habitId: number;
  habitImage: string;
  title: string;
  body: string;
  isBooked: boolean;
}

export const habitWrapperData: Array<habitWrapperDataType> = [
  {
    habitId: 1,
    habitImage: '/image/running.png',
    title: '달리기 3km',
    body: '매일 아침에 3km 달리기를 실천합니다. 상쾌한 아침을 맞이하며 건강도 챙겨보세요!',
    isBooked: false,
  },
  {
    habitId: 2,
    habitImage: '/image/running.png',
    title: '달리기 3km',
    body: '매일 아침에 3km 달리기를 실천합니다. 상쾌한 아침을 맞이하며 건강도 챙겨보세요!',
    isBooked: false,
  },
  {
    habitId: 3,
    habitImage: '/image/running.png',
    title: '달리기 3km',
    body: '매일 아침에 3km 달리기를 실천합니다. 상쾌한 아침을 맞이하며 건강도 챙겨보세요!',
    isBooked: false,
  },
  {
    habitId: 4,
    habitImage: '/image/running.png',
    title: '달리기 3km',
    body: '매일 아침에 3km 달리기를 실천합니다. 상쾌한 아침을 맞이하며 건강도 챙겨보세요!',
    isBooked: false,
  },
];
