import { CommentNode, UserAccount } from "./interface";

export const GenerateCommentObject = (
  postID: string,
  account: UserAccount,
  content: string,
  parentId?: string
): CommentNode => {
  return {
    children: [],
    user: account,
    comment: {
      id: "",
      postId: postID,
      userId: account.user.id,
      content: content,
      parentId: parentId,
      dateCreated: new Date(),
      likes: 0,
      dislikes: 0,
    },
  };
};

export const TraverseCommentTree = () => {};

export const getTotalChildren = (comment: CommentNode): number => {
  return getTotalChildrenAux(comment) - 1;
};

export const getTotalChildrenAux = (comment: CommentNode): number => {
  return comment.children.reduce(
    (acc, child) => acc + getTotalChildrenAux(child),
    1
  );
};