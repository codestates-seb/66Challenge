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

  const message = {
    notification: {
      title: 'nextjs 테스트 데이터',
      body: '데이터 내용내용',
    },
    token: token,
  };
  admin
    .messaging()
    .send(message)
    .then((res) => console.log('success:', res))
    .catch((e) => console.log('erroe:', e));

  res.status(200);
}
