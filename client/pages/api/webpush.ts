import type { NextApiRequest, NextApiResponse } from 'next';
const admin = require('firebase-admin');

const serAccount = require('../../firebase-admin.json');
if (!admin.apps.length) {
  admin.initializeApp({
    credential: admin.credential.cert(serAccount),
  });
}
const arr = [
  [
    '명심보감',
    '무릇 희롱하여 놀기만 하는 것은 이익됨이 없고, 오직 부지런한 것만이 공을 이룬다.',
  ],
  ['한비자', '교묘하게 속이는 것보다 졸하더라도 성실한것이 좋다.'],
  ['톨스토이', '이마에 땀을 흘리며 그날의 빵을 구하라'],
  ,
  ['스티브 잡스', 'Stay hungry, Stay foolish'],
  ['메이슨 쿨리', '낭비한 시간에 대한 후회는 더 큰 시간 낭비이다.'],
  ['니체', '오랫동안 꿈을 그리는 사람은 마침내 그 꿈을 닮아간다.'],
  ['셰익스피어', '결코 끌 수 없는 열정으로 삶을 살아라'],
  ['윈스턴 처칠', '지옥을 겪고 있다면 계속 겪어 나가라.'],
  [
    '우드로 윌슨',
    '천재성은 하늘이 주신 인내심이다. 천재성은 나 역시 가질 수 없지만, 인내심은 모두가 가질 수 있다.',
  ],
  [
    '토마스 제퍼슨',
    '아무 하는 일 없이 시간을 허비하지 않겠다고 맹세하라. 우리가 항상 뭔가를 한다면 놀라우리만치 많은 일을 해낼 수 있다.',
  ],
  ['장 칼뱅', '근면과 성실로 재산을 모은 것은 신의 섭리에 어긋나지 않는다.'],
  [
    '벤자민 프랭클린',
    '일찍 자고 일찍 일어나는 것은 사람을 건강하게 하고 부자로 만들고 현명하게 한다.',
  ],
  [
    '강태공',
    '부지런한 것은 돈으로 살 수 없는 보배이며, 근신은 몸을 지키는 부적이다.',
  ],
  [
    '미켈란젤로',
    '내가 지금의 경지에 이르기 위해 얼마나 열심히 일하고 또 일 했는지 사람들이 안다면 아마 하나도 위대해 보이지 않을 것이다.',
  ],
  [
    '박명수',
    '늦었다고 생각할 때가 진짜 너무 늦었다. 그러니 지금 당장 시작하라.',
  ],
];

export default function webpush(req: NextApiRequest, res: NextApiResponse) {
  const token: string = req.body.token;
  const arrIdx = Math.floor(Math.random() * arr.length);
  //지금은 토픽별로 메시지를 보내는 것 이지만 1000개까지 가능 등록하는 순간 데이터 베이스는 필요가 없을 것 같음? 구독 취소까지 가능하다.
  //여러기기를 보낼거면 500개까지 배열로 만들어서 sendMulticast 방식을 이용해서 반복으로 돌려서 보내면 되지 않을까?
  admin
    .messaging()
    .subscribeToTopic(token, 'public')
    .then((res) => console.log('success:', res))
    .catch((e) => console.log('error:', e));

  const message = {
    notification: {
      title: '66 Challenge',
      body: `${arr[arrIdx][1]} -${arr[arrIdx][0]}-`,
    },
    webpush: {
      fcm_options: {
        link: 'https://66challenge.shop',
      },
    },
    topic: 'public',
  };
  admin
    .messaging()
    .send(message)
    .then((res) => console.log('success:', res))
    .catch((e) => console.log('error:', e));

  res.status(200);
}
