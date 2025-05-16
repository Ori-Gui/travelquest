
import { Review } from "@/types";
import { StarIcon } from "lucide-react";

interface ReviewCardProps {
  review: Review;
}

const ReviewCard: React.FC<ReviewCardProps> = ({ review }) => {
  // Function to format date
  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('ko-KR', { year: 'numeric', month: 'numeric', day: 'numeric' });
  };

  return (
    <div className="retro-border bg-white mb-4">
      <h3 className="font-pixel text-lg font-bold">{review.title}</h3>
      <div className="flex space-x-1 my-2">
        {[...Array(5)].map((_, i) => (
          <StarIcon 
            key={i} 
            className={i < review.rating ? "fill-quest-yellow text-quest-yellow" : "text-gray-300"} 
            size={16} 
          />
        ))}
      </div>
      <p className="text-sm text-gray-700 my-2">{review.content}</p>
      {review.images && review.images.length > 0 && (
        <div className="flex space-x-2 mt-2 overflow-x-auto pb-2">
          {review.images.map((image, index) => (
            <img
              key={index}
              src={image}
              alt={`Review image ${index + 1}`}
              className="w-16 h-16 object-cover rounded border border-gray-300"
            />
          ))}
        </div>
      )}
      <div className="text-xs text-gray-500 mt-2">
        작성일: {formatDate(review.createdAt)}
      </div>
    </div>
  );
};

export default ReviewCard;
