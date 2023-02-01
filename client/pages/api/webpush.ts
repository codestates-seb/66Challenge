import type { NextApiRequest, NextApiResponse } from 'next';
const admin = require('firebase-admin');

const serAccount = require('../../firebase-admin.json');
if (!admin.apps.length) {
  admin.initializeApp({
    credential: admin.credential.cert(serAccount),
  });
}

export default function webpush(req: NextApiRequest, res: NextApiResponse) {
  const token: string = req.body.token;
  //지금은 토픽별로 메시지를 보내는 것 이지만 1000개까지 가능 등록하는 순간 데이터 베이스는 필요가 없을 것 같음? 구독 취소까지 가능하다.
  //여러기기를 보낼거면 500개까지 배열로 만들어서 sendMulticast 방식을 이용해서 반복으로 돌려서 보내면 되지 않을까?
  admin
    .messaging()
    .subscribeToTopic(token, 'public')
    .then((res) => console.log('success:', res))
    .catch((e) => console.log('error:', e));

  res.status(200);
}
