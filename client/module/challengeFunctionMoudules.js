// export async function getAllChallenges(page, size) {
//   try {
//     const response = await axios
//       .get(`${process.env.NEXT_PUBLIC_SERVER_URL}/challenges`)
//       .then((res) => console.log(res));
//     return response;
//   } catch (e) {
//     console.error(e);
//   }
// }
// export async function postChallenge({ cookie, userId, habitId }) {
//   try {
//     const response = await axios
//       .post(
//         `${process.env.NEXT_PUBLIC_SERVER_URL}/challenges`,
//         {
//           userId,
//           habitId,
//         },
//         {
//           headers: {
//             Authorization: cookie,`,
//           },
//         },
//       )
//       .then((res) => console.log(res));
//     return response;
//   } catch (e) {
//     console.error(e);
//   }
// }
// export async function getChallengeAuths(cookie) {
//   try {
//     const response = await axios
//       .get(`${process.env.NEXT_PUBLIC_SERVER_URL}/auths`, {
//         headers: {
//           Authorization: cookie,,
//         },
//       })
//       .then((res) => console.log(res));
//     return response;
//   } catch (e) {
//     console.error(e);
//   }
// }
export async function postAuth({ challengeId, body, cookie }) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/challenges/${challengeId}/auths`,
        { body },
        {
          headers: {
            Authorization: cookie,
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function deleteAuth({ cookie, challengeId, authId }) {
  try {
    const response = await axios
      .delete(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/challenges/${challengeId}/auths/${authId}`,
        {
          headers: {
            Authorization: cookie,
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function patchAuths({ cookie, challengeId, authId, body }) {
  try {
    const response = await axios
      .patch(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/challenges/${challengeId}/auths/${authId}`,
        { authId, body },
        {
          headers: {
            Authorization: cookie,
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function getUserChallenges(userId, cookie, page, size) {
  try {
    const response = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/challenges/users/${userId}?page=${page}&size=${size}`,
        {
          headers: {
            Authorization: cookie,
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
