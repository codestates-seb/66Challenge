importScripts(
  'https://www.gstatic.com/firebasejs/9.2.0/firebase-app-compat.js',
);
importScripts(
  'https://www.gstatic.com/firebasejs/9.2.0/firebase-messaging-compat.js',
);
const firebaseConfig = {
  apiKey: 'AIzaSyCyu7PAZoZkoFeihqUfT11U27iNK_PKnYg',
  authDomain: 'challenge-65eff.firebaseapp.com',
  projectId: 'challenge-65eff',
  storageBucket: 'challenge-65eff.appspot.com',
  messagingSenderId: '312532099846',
  appId: '1:312532099846:web:b0897878c226dda13db487',
  measurementId: 'G-4EM46N63HM',
};

firebase.initializeApp(firebaseConfig);
// Retrieve an instance of Firebase Messaging so that it can handle background
// messages.
// const messaging = firebase.messaging();
// messaging.onBackgroundMessage((payload) => {
//   // Customize notification here
//   const notificationTitle = payload.notification.title;
//   const notificationOptions = {
//     body: payload.notification.body,
//     icon: '/image/logo.svg',
//   };
//   console.log(payload);
//   self.registration.showNotification(notificationTitle, notificationOptions);
// });
self.addEventListener('install', function (e) {
  console.log('fcm sw install..');
  self.skipWaiting();
});

self.addEventListener('activate', function (e) {
  console.log('fcm sw activate..');
});
self.addEventListener('push', function (event) {
  const payload = event.data.json();
  const title = payload.data.title;
  const options = {
    body: payload.data.body,
    icon: '/image/logo.svg',
    data: payload.data.link,
  };
  event.waitUntil(self.registration.showNotification(title, options));
});
self.addEventListener('notificationclick', function (event) {
  event.notification.close();
  event.waitUntil(clients.openWindow(event.notification.data));
});
