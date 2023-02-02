interface slideDataType {
  bgImgUrl: string;
  bannerLink: string;
}

export const slideData: Array<slideDataType> = [
  {
    bgImgUrl: '/image/slideImg/homeMain/slideImg1.svg',
    bannerLink: '/user/guest',
  },
  {
    bgImgUrl: '/image/slideImg/homeMain/slideImg2.svg',
    bannerLink: '',
  },
  {
    bgImgUrl: '/image/slideImg/homeMain/slideImg3.svg',
    bannerLink: '',
  },
];

export const subSlideData: Array<slideDataType> = [
  {
    bgImgUrl: '/image/slideImg/homeSub/slideImg1.svg',
    bannerLink: '/contents/youtube',
  },
  {
    bgImgUrl: '/image/slideImg/homeSub/slideImg2.svg',
    bannerLink: '/contents/book',
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

interface youtubeLinkType {
  link: string;
  category: string;
}

export const youtubeLink: Array<youtubeLinkType> = [
  {
    link: 'https://www.youtube.com/embed/Y5i1v_3Mdl8',
    category: 'selfcare',
  },
  {
    link: 'https://www.youtube.com/embed/B9LIYb3BIQ8',
    category: 'selfcare',
  },
  {
    link: 'https://www.youtube.com/embed/01aV_PbiT30',
    category: 'selfcare',
  },
  {
    link: 'https://www.youtube.com/embed/xieox2a0wGo',
    category: 'selfcare',
  },
  {
    link: 'https://www.youtube.com/embed/jdxujfAz1zA',
    category: 'selfcare',
  },
  {
    link: 'https://www.youtube.com/embed/mx0EODaNd6g',
    category: 'study',
  },
  {
    link: 'https://www.youtube.com/embed/nDFrSyh3Ylo',
    category: 'study',
  },
  {
    link: 'https://www.youtube.com/embed/A5CCe1-Q0t4',
    category: 'exercise',
  },
  {
    link: 'https://www.youtube.com/embed/iw3eEjp6wYU',
    category: 'exercise',
  },
  {
    link: 'https://www.youtube.com/embed/LDOn0eWTd4o',
    category: 'selfcare',
  },
  {
    link: 'https://www.youtube.com/embed/Nhzeggclrlw',
    category: 'selfcare',
  },
  {
    link: 'https://www.youtube.com/embed/sCC0QlesMkc',
    category: 'exercise',
  },
  {
    link: 'https://www.youtube.com/embed/MPAxkj6RNZk',
    category: 'exercise',
  },
  {
    link: 'https://www.youtube.com/embed/C7tx1U8QfiE',
    category: 'selfcare',
  },
  {
    link: 'https://www.youtube.com/embed/leHu_0ZqlIQ',
    category: 'habit',
  },
  {
    link: 'https://www.youtube.com/embed/xiE2wL9hS2w',
    category: 'habit',
  },
  {
    link: 'https://www.youtube.com/embed/9xYATLdxdck',
    category: 'habit',
  },
  {
    link: 'https://www.youtube.com/embed/iVqvX90ucxI',
    category: 'habit',
  },
  {
    link: 'https://www.youtube.com/embed/pzvzsqcBKa8',
    category: 'habit',
  },
  {
    link: 'https://www.youtube.com/embed/zaxG5oDJsN8',
    category: 'habit',
  },
];

interface habitWrapperDataType {
  habitId: number;
  thumbImgUrl: string;
  title: string;
  body: string;
  isBooked: boolean;
}
