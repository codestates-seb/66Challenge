import firebase from 'firebase';

export async function getToken() {
  if (firebase.messaging.isSupported() === false) {
    console.log('isSupported: ', firebase.messaging.isSupported());
    return null;
  }
  const permission = await Notification.requestPermission();
  if (permission !== 'granted') return;

  const messaging = firebase.messaging();
  const token = await messaging.getToken({
    vapidKey: process.env.NEXT_PUBLIC_FIREBASE_MESSAGING_VAPID_KEY,
  });
  messaging.onMessage((payload) => alert(payload.notification.body));
  console.log(token);
  return token;
}
