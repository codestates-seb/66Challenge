import { getCookie } from './cookies';
import axios, { AxiosError } from 'axios';
export async function postAuth({ challengeId, body }) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/challenges/${challengeId}/auths`,
        body,
        {
          headers: {
            Authorization: getCookie('accessJwtToken'),
            'Content-Type': 'multipart/form-data',
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.log(e);
  }
}
export async function deleteAuth({ challengeId, authId }) {
  try {
    const response = await axios
      .delete(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/challenges/${challengeId}/auths/${authId}`,
        {
          headers: {
            Authorization: getCookie('accessJwtToken'),
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function patchAuths({ challengeId, authId, body }) {
  try {
    const response = await axios
      .patch(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/challenges/${challengeId}/auths/${authId}`,
        { authId, body },
        {
          headers: {
            Authorization: getCookie('accessJwtToken'),
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function getUserSuccessChallenges(userId: number) {
  try {
    const response = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/challenges/users/${userId}/success?page=1&size=90000`,
        {
          headers: {
            Authorization: getCookie('accessJwtToken'),
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function getUserChallenges(userId: number) {
  try {
    const response = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/challenges/users/${userId}/challenge?page=1&size=90000`,
        {
          headers: {
            Authorization: getCookie('accessJwtToken'),
          },
        },
      )
      .then((res) => res.data);
    return response;
  } catch (e) {
    if (e instanceof AxiosError) {
      return e.response.status;
    }
  }
}
