import axios from 'axios';
import type { UserGeneralProps } from './moduleInterface';
import { getCookie } from './cookies';

export async function getUserBookmarks({ userId }: UserGeneralProps) {
  try {
    const response = await axios
      .get(`${process.env.NEXT_PUBLIC_SERVER_URL}/bookmarks/${userId}`, {
        headers: {
          // Authorization: getCookie('accessJwtToken'),
        },
      })
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
