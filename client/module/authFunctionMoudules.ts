// import axios from 'axios';
// import { getCookie } from './cookies';

// export async function getAllAuth({ habitId, page, size }) {
//   try {
//     const response = await axios
//       .get(`${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/auths?page={}`, {
//         headers: {
//           Authorization: getCookie('accessJwtToken'),
//         },
//       })
//       .then((res) => console.log(res));
//     return response;
//   } catch (e) {
//     console.error(e);
//   }
// }
