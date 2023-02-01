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
  const [authState, setAuthState] = useState({ state: '', type: '' });
  const dispatch = useAppDispatch();
  if (!firebase.apps.length) {
    firebase.initializeApp(firebaseConfig);
  } else {
    firebase.app();
  }
  const onNotificationHandle = async () => {
    setAuthState({ state: 'none', type: 'post' });
    setTimeout(() => {
      setAuthState({ state: '', type: 'post' });
    }, 1500);
    const token = await getToken();
    dispatch(notificationToken(token));
    axios
      .post(`${process.env.NEXT_PUBLIC_API_URL}/api/webpush`, { token })
      .then(() => {
        console.log('send');
      });
    // axios.post('https://0280-222-110-121-44.jp.ngrok.io/message', {
    //   message: token,
    // });
  };
  const onNotificationCancelHandle = async () => {
    setAuthState({ state: 'none', type: 'cancel' });
    setTimeout(() => {
      setAuthState({ state: '', type: 'cancel' });
    }, 1500);
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
    <div className="flex w-full relative">
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
          authState.state === 'none' ? 'flex' : 'hidden'
        } absolute bg-white border-2 border-subColor rounded-full w-10 justify-center items-center animate-dropDown -bottom-10 aspect-square left-1`}
      >
        <span className="text-subColor font-semibold">
          {authState.type === 'post' ? '등록' : '취소'}
        </span>
      </div>
    </div>
  );
}
