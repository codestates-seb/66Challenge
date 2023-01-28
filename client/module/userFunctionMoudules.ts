import axios, { AxiosResponse } from 'axios';
import type {
  SignUpProps,
  UserGeneralProps,
  PatchUserInfoProps,
  getUserCertificateProps,
  profileImageProps,
} from './moduleInterface';
/*사용하는 방법 
1. 쓰고자하는 함수를 찾는다
2. 사용하고자하는 페이지에서 import 해온다
3. 데이터를 입맛에 맞게 넣는다.
4. then 뒷부분 console.log 로 받아오는 데이터를 확인한다.
5. 확인 후 리턴해야 할 값을 명확히 적는다.
6. 끝 */
import { getCookie } from './cookies';
export async function postUserSignUp({
  email,
  username,
  password,
}: SignUpProps) {
  try {
    const response = await axios
      .post(`${process.env.NEXT_PUBLIC_SERVER_URL}/users`, {
        email,
        username,
        password,
      })
      .then((res) => res.status);
    return response;
  } catch (e) {
    return e.response.status;
  }
}

export async function getUserEmailOverlapVerify(
  email: string,
): Promise<boolean> {
  try {
    const response: boolean = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/users/emails/check?email=${email}`,
      )
      .then((res) => res.data);
    console.log(response);
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function getUsernameOverlapVerify(
  username: string,
): Promise<boolean> {
  try {
    const response: boolean = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/users/usernames/check?username=${username}`,
      )
      .then((res) => res.data);
    console.log(response);
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function getUserInfo({ userId }: UserGeneralProps) {
  try {
    const response = await axios
      .get(`${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}`, {
        headers: {
          Authorization: getCookie('accessJwtToken'),
        },
      })
      .then((res) => res.data);
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function deleteUser({ userId }: UserGeneralProps) {
  try {
    const response = await axios
      .delete(`${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}`, {
        headers: {
          Authorization: getCookie('accessJwtToken'),
        },
      })
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function patchUserInfo({
  userId,
  username,
  password,
}: PatchUserInfoProps) {
  try {
    const response = await axios
      .patch(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}`,
        {
          username,
          userId,
          password,
        },
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
export async function getUserCertificate({
  userId,
  habitId,
}: getUserCertificateProps) {
  try {
    const response = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}/habits/${habitId}/certificates`,
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
export async function getUserHabitsCategories({ userId }: UserGeneralProps) {
  try {
    const response = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}/habits/categories`,
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
export async function getUserHabitsHosts({ userId }: UserGeneralProps) {
  try {
    const response = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}/habits/hosts`,
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
export async function getPasswordCheck({ userId }: UserGeneralProps) {
  try {
    const response = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}/passwords/check`,
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
export async function profileChange({
  userId,
  profileImage,
}: profileImageProps) {
  try {
    const response = await axios.patch(
      `${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}`,
      {
        file: profileImage,
      },
      {
        headers: {
          Authorization: getCookie('accessJwtToken'),
          'Content-Type': 'multipart/form-data',
        },
      },
    );
    return response;
  } catch (e) {
    console.error(e);
  }
}

export async function profileDelete({ userId }) {
  try {
    const response = await axios.delete(
      `${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}/profiles`,
      {
        headers: {
          Authorization: getCookie('accessJwtToken'),
        },
      },
    );
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function postUserEmailAuth(email: string) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/users/email-verification-requests?email=${email}`,
        {
          email,
        },
      )
      .then((res) => res.status);
  } catch (e) {
    return e.response.status;
  }
}
