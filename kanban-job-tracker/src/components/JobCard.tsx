import { useSortable } from '@dnd-kit/sortable';
import { CSS } from '@dnd-kit/utilities';
import { Calendar, X } from 'lucide-react';
import type { Job } from '../types';

interface JobCardProps {
  job: Job;
  isOverlay?: boolean;
  onDelete?: (id: string) => void;
}

export function JobCard({ job, isOverlay, onDelete }: JobCardProps) {
  const { attributes, listeners, setNodeRef, transform, transition, isDragging } = useSortable({ id: job.id });

  const style = {
    transform: CSS.Transform.toString(transform),
    transition,
    // NEW: Forces the browser to use the GPU for smoother dragging
    willChange: 'transform', 
  };

  const getPriorityColor = (priority: string) => {
    if (priority === 'High') return 'bg-red-500 shadow-[0_0_8px_rgba(239,68,68,0.8)]';
    if (priority === 'Medium') return 'bg-yellow-400 shadow-[0_0_8px_rgba(250,204,21,0.8)]';
    return 'bg-green-500 shadow-[0_0_8px_rgba(34,197,94,0.8)]';
  };

  const handleDeleteClick = (e: React.MouseEvent) => {
    e.stopPropagation();
    if (window.confirm(`Delete the ${job.role} role at ${job.company}?`)) {
      if (onDelete) onDelete(job.id);
    }
  };

  if (isOverlay) {
    return (
      <div className="bg-white dark:bg-[#1a1a1a] p-5 rounded-2xl border border-blue-500/50 shadow-2xl cursor-grabbing opacity-90 rotate-2 w-72 will-change-transform">
        <h3 className="font-semibold text-gray-900 dark:text-white">{job.role}</h3>
        <p className="text-sm text-gray-500 dark:text-gray-400">{job.company}</p>
      </div>
    );
  }

  return (
    <div
      ref={setNodeRef}
      style={style}
      className={`bg-white dark:bg-[#141414] p-5 rounded-2xl border border-gray-200 dark:border-gray-800 hover:border-gray-400 dark:hover:border-gray-600 transition-colors group relative ${
        isDragging ? 'opacity-40 scale-95' : 'opacity-100'
      }`}
    >
      <div {...attributes} {...listeners} className="cursor-grab active:cursor-grabbing">
        <div className="flex justify-between items-center mb-3">
          <span className="bg-gray-100 dark:bg-gray-800/80 text-gray-600 dark:text-gray-300 text-[10px] font-bold px-2.5 py-1 rounded-full uppercase tracking-wider">
            {job.company}
          </span>
          <div className="flex items-center gap-2">
            <span className={`w-3 h-3 rounded-full ${getPriorityColor(job.priority)}`} title={`${job.priority} Priority`}></span>
            <button onClick={handleDeleteClick} className="text-gray-400 hover:text-red-500 transition-colors">
              <X size={16} />
            </button>
          </div>
        </div>

        <h3 className="font-semibold text-gray-900 dark:text-gray-100 text-lg leading-tight mb-2">{job.role}</h3>
        
        <div className="flex items-center gap-1.5 text-xs text-gray-500 mt-4">
          <Calendar size={14} />
          <span>Added {job.dateAdded}</span>
        </div>
      </div>
    </div>
  );
}