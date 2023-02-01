import axios, { AxiosError } from 'axios';
import { getCookie } from './cookies';
import type {
  getReviewsProps,
  postHabitReviewProps,
  deleteHabitReviewProps,
  patchHabitReviewProps,
} from './moduleInterface';

export async function getHabitReviews({
  habitId,
  page,
  size,
}: getReviewsProps) {
  try {
    const response = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/reviews?page=${page}&size=${size}`,
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function postHabitReview({
  habitId,
  userId,
  body,
  score,
}: postHabitReviewProps) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/reviews?userId=${userId}`,
        { body, score },
      )
      .then((res) => res);
    return response.status;
  } catch (e) {
    if (e instanceof AxiosError) {
      return e.response.status;
    }
  }
}

export async function deleteHabitReview({
  habitId,
  reviewId,
}: deleteHabitReviewProps) {
  try {
    const response = await axios
      .delete(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/reviews/${reviewId}`,
        {
          headers: {
            // Authorization: getCookie('accessJwtToken'),
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function patchHabitReview({
  habitId,
  reviewId,
  body,
  score,
}: patchHabitReviewProps) {
  try {
    const response = await axios
      .patch(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/reviews/${reviewId}`,
        { body, score, reviewId },
        {
          headers: {
            // Authorization: getCookie('accessJwtToken'),
          },
        },
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
