import firebase from 'firebase';

export async function getToken() {
  if (firebase.messaging.isSupported() === false) {
    console.log('isSupported: ', firebase.messaging.isSupported());
    return null;
  }
  const permission = await Notification.requestPermission();
  if (permission !== 'granted') return;

  const messaging = firebase.messaging();
  messaging.onMessage(function (payload) {
    console.log('onMessage: ', payload);
    const title = payload.notification.title;
    const options = {
      body: payload.notification.body,
      icon: '/image/logo.svg',
    };
    const notification = new Notification(title, options);
  });

  const token = await messaging.getToken({
    vapidKey: process.env.NEXT_PUBLIC_FIREBASE_MESSAGING_VAPID_KEY,
  });
  console.log(token);
  return token;
}
