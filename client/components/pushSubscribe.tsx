import {
  IoIosNotificationsOutline,
  IoIosNotificationsOff,
} from 'react-icons/io';
import firebase from 'firebase';
import { getToken } from '../util/firebase';
import { useAppDispatch } from '../ducks/store';
import {
  notificationToken,
  notificationTokenDelete,
} from '../ducks/loginIdentitySlice';
import axios from 'axios';
import { useState } from 'react';
const firebaseConfig = {
  apiKey: process.env.NEXT_PUBLIC_FIREBASE_API_KEY,
  authDomain: process.env.NEXT_PUBLIC_FIREBASE_AUTH_DOMAIN,
  projectId: process.env.NEXT_PUBLIC_FIREBASE_PROJECT_ID,
  storageBucket: process.env.NEXT_PUBLIC_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: process.env.NEXT_PUBLIC_FIREBASE_MESSAGING_SENDER_ID,
  appId: process.env.NEXT_PUBLIC_FIREBASE_APP_ID,
  measurementId: process.env.NEXT_PUBLIC_FIREBASE_MEASUREMENT_ID,
};
export function PushSubscribe() {
  const [authState, setAuthState] = useState('');
  const dispatch = useAppDispatch();
  if (!firebase.apps.length) {
    firebase.initializeApp(firebaseConfig);
  } else {
    firebase.app();
  }
  const onNotificationHandle = async () => {
    const token = await getToken();
    dispatch(notificationToken(token));
    axios.post(`${process.env.NEXT_PUBLIC_API_URL}/api/webpush`, { token });
    // axios.post('https://0280-222-110-121-44.jp.ngrok.io/message', {
    //   message: token,
    // });
  };
  const onNotificationCancelHandle = async () => {
    const token = await getToken();

    dispatch(notificationTokenDelete());
    axios.post(`${process.env.NEXT_PUBLIC_API_URL}/api/webpushcancel`, {
      token,
    });
    // axios.post('https://0280-222-110-121-44.jp.ngrok.io/message', {
    //   message: token,
    // });
  };
  return (
    <div className="flex">
      <IoIosNotificationsOutline
        className="h-6 w-6"
        onClick={onNotificationHandle}
      />
      <IoIosNotificationsOff
        className="h-6 w-6"
        onClick={onNotificationCancelHandle}
      />
      <div
        className={`${
          authState === 'none' ? 'flex' : 'hidden'
        } absolute w-3/4 bg-white border-2 border-subColor top-20 rounded-full justify-center h-10 items-center animate-dropDown`}
      >
        <span className="text-subColor font-semibold">
          이메일 인증을 하셔야 합니다!
        </span>
      </div>
    </div>
  );
}
