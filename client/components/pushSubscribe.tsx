import { IoMdNotificationsOutline } from 'react-icons/io';
import firebase from 'firebase';
import { getToken } from '../util/firebase';
import { useAppDispatch } from '../ducks/store';
import { notificationToken } from '../ducks/loginIdentitySlice';
import axios from 'axios';
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
  const dispatch = useAppDispatch();
  if (!firebase.apps.length) {
    firebase.initializeApp(firebaseConfig);
  } else {
    firebase.app();
  }
  const onNotificationHandle = async () => {
    let token = await getToken();
    dispatch(notificationToken(token));
    axios.post(`${process.env.NEXT_PUBLIC_API_URL}/api/webpush`, { token });
  };
  return (
    <div>
      <IoMdNotificationsOutline
        className="h-6 w-6"
        onClick={onNotificationHandle}
      />
    </div>
  );
}
